package kt.kotlin.activity

import android.os.Build
import android.security.keystore.KeyProperties
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties.*
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

class MainActivity : AppCompatActivity() {
    private val KEY_NAME = "my_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            // Android 6.0 及以上版本才支持硬件加密，因此不需要检查 BI 锁状态
            return
        }

        try {
            val keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)

            if (!keyStore.isKeyEntry(KEY_NAME)) {
                // 如果密钥不存在，则生成一个新的密钥
                val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
                val builder = KeyGenParameterSpec.Builder(KEY_NAME, PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
                    .setBlockModes(BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(ENCRYPTION_PADDING_PKCS7)

                keyGenerator.init(builder.build())
                keyGenerator.generateKey()
            }

            val cipher = Cipher.getInstance("${KeyProperties.KEY_ALGORITHM_AES}/${KeyProperties.BLOCK_MODE_CBC}/${KeyProperties.ENCRYPTION_PADDING_PKCS7}")
            val secretKey = keyStore.getKey(KEY_NAME, null) as SecretKey

            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        } catch (e: KeyPermanentlyInvalidatedException) {
            // 如果 BI 锁已解锁，则会导致 KeyPermanentlyInvalidatedException 异常
            finish()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}