import java.io.File
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.experimental.and

/**
 * 校验APK的V2签名是否有效
 *
 * @param apkFile APK文件
 * @return true表示签名有效，false表示签名无效
 */
fun isV2SignatureValid(apkFile: File): Boolean {
    try {
        val signatureBlock = getApkSigningBlock(apkFile)
        if (signatureBlock == null) {
            // APK没有签名块
            return false
        }

        // 获取签名块的长度和签名块的内容
        val signatureBlockLength = signatureBlock.remaining()
        val signatureBlockBytes = ByteArray(signatureBlockLength)
        signatureBlock.get(signatureBlockBytes)

        // 对签名块进行SHA256摘要
        val digest = MessageDigest.getInstance("SHA-256")
        digest.update(signatureBlockBytes)
        val signatureBlockDigest = digest.digest()

        // 从APK中获取V2签名
        val apkSigningBlocks = getApkSigningBlocks(apkFile)
        for (apkSigningBlock in apkSigningBlocks) {
            // 获取签名块的长度和签名块的内容
            val apkSigningBlockLength = apkSigningBlock.remaining()
            val apkSigningBlockBytes = ByteArray(apkSigningBlockLength)
            apkSigningBlock.get(apkSigningBlockBytes)

            // 对签名块进行SHA256摘要
            digest.reset()
            digest.update(apkSigningBlockBytes)
            val apkSigningBlockDigest = digest.digest()

            if (Arrays.equals(signatureBlockDigest, apkSigningBlockDigest)) {
                // 找到匹配的签名块，返回true
                return true
            }
        }

        // 没有匹配的签名块，返回false
        return false
    } catch (e: Exception) {
        e.printStackTrace()
        return false
    }
}

/**
 * 从APK中获取V2签名块
 *
 * @param apkFile APK文件
 * @return V2签名块的ByteBuffer，如果APK没有V2签名，则返回null
 */
@Throws(IOException::class)
private fun getApkSigningBlock(apkFile: File): ByteBuffer? {
    val apkSigningBlockId = ByteBuffer.wrap("APK Sig Block 42".toByteArray(Charsets.US_ASCII))
    val apkSigningBlockOffset = getCentralDirectoryOffset(apkFile)

    // 读取ZIP中的所有数据
    val apkBytes = ByteArray(apkFile.length().toInt())
    apkFile.inputStream().use { input ->
        input.read(apkBytes)
    }

    // 从APK的结尾开始搜索APK签名块
    var offset = apkFile.length() - 24
    while (offset >= apkSigningBlockOffset) {
        // 读取块大小
        val dataSize = readIntLittleEndian(apkBytes, offset)
        val magic = readIntLittleEndian(apkBytes, offset + 4)

        if (dataSize == offset - apkSigningBlockOffset &&
            magic == apkSigningBlockId.int
        ) {
            // 找到APK签名块，返回它的ByteBuffer
            return ByteBuffer.wrap(apkBytes, offset, dataSize).order(ByteOrder.LITTLE_ENDIAN)
        }

        // 向前移动一个位置
        offset -= 8
    }

    // 没有找到APK签名块
    return null
}

/**
 * 获取APK的中央目录偏移量
 *
 * @param apkFile APK文件
 * @return 中央目录偏移量
 */
@Throws(IOException::class)
private fun getCentralDirectoryOffset(apkFile: File): Long {
    // 读取ZIP文件的结尾，查找中央目录结束标记
    var offset = apkFile.length() - 22
    while (offset >= 0) {
        val headerId = readIntLittleEndian(apkFile, offset)
        if (headerId == 0x06054b50) {
            // 找到中央目录结束标记，返回中央目录偏移量
            return readIntLittleEndian(apkFile, offset + 16).toLong()
        }

        // 向前移动一个位置
        offset -= 1
    }

    // 没有找到中央目录结束标记
    throw IOException("Invalid APK file: Central directory not found")
}

/**
 * 读取一个32位整数（小端序）
 *
 * @param data 字节数组
 * @param offset 偏移量
 * @return 32位整数
 */
private fun readIntLittleEndian(data: ByteArray, offset: Int): Int {
    return (data[offset].toInt() and 0xff) or
            ((data[offset + 1].toInt() and 0xff) shl 8) or
            ((data[offset + 2].toInt() and 0xff) shl 16) or
            ((data[offset + 3].toInt() and 0xff) shl 24)
}

/**
 * 读取一个32位整数（小端序）
 *
 * @param file 文件
 * @param offset 偏移量
 * @return 32位整数
 */
@Throws(IOException::class)
private fun readIntLittleEndian(file: File, offset: Long): Int {
    val buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN)
    file.inputStream().use { input ->
        input.channel.position(offset)
        input.channel.read(buffer)
    }
    buffer.flip()
    return buffer.int
}

/**
 * 从APK中获取所有V2签名块
 *
 * @param apkFile APK文件
 * @return 所有V2签名块的ByteBuffer列表
 */
@Throws(IOException::class, NoSuchAlgorithmException::class)
private fun getApkSigningBlocks(apkFile: File): List<ByteBuffer> {
    val signingBlocks = mutableListOf<ByteBuffer>()
    val apkSigningBlock = getApkSigningBlock(apkFile)
    if (apkSigningBlock != null) {
        // 将APK签名块添加到签名块列表中
        signingBlocks.add(apkSigningBlock)
    }

    // 读取ZIP中的所有数据
    val apkBytes = ByteArray(apkFile.length().toInt())
    apkFile.inputStream().use { input ->
        input.read(apkBytes)
    }

   // 从ZIP的开始处开始搜索V2签名块
    var offset = 0
    while (offset < apkBytes.size) {
        // 读取块大小
        val dataSize = readIntLittleEndian(apkBytes, offset)
        val headerId = readIntLittleEndian(apkBytes, offset + 4)

        if (dataSize >= 8 && headerId == 0x7109871a) {
            // 找到V2签名块，将其添加到签名块列表中
            val signingBlock = ByteBuffer.wrap(apkBytes, offset, dataSize).order(ByteOrder.LITTLE_ENDIAN)
            signingBlocks.add(signingBlock)
        }

        // 向前移动一个位置
        offset += dataSize + 4
    }

    // 返回签名块列表
    return signingBlocks
}