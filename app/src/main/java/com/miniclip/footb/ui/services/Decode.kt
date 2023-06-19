package com.miniclip.footb.ui.services

import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import org.json.JSONObject
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

@ObfustringThis
class Decode {
    companion object {
        fun decodeAES(data: String, nonce: String): JSONObject? {
            return try {
                val algorithmName = "algorithmName"
                val keyFacebook = decodeHex(FBHandler.DECRYPTION_KEY)
                val dataByteArray = decodeHex(data)
                val secretKeyNonce = decodeHex(nonce)

                val specKey = SecretKeySpec(keyFacebook, algorithmName)
                val secretKeyNonceSpec = IvParameterSpec(secretKeyNonce)
                val cipher: Cipher? = Cipher.getInstance(algorithmName)
                cipher?.init(Cipher.DECRYPT_MODE, specKey, secretKeyNonceSpec)

                if (cipher != null) {
                    val finalResult: ByteArray = cipher.doFinal(dataByteArray)
                    JSONObject(String(finalResult))
                } else null

            } catch (e: Exception) {
                null
            }
        }

        private fun decodeHex(encryptString: String?): ByteArray? {
            val length = encryptString?.length ?: return null
            return if (length % 2 == 0) {
                encryptString.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
            } else null
        }
    }
}