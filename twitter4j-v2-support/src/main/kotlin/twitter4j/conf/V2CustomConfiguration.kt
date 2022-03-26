package twitter4j.conf

import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.util.*

/**
 * custom configurations for OAuth 2.0 with PKCE
 *
 * like [PropertyConfiguration]
 */
class V2CustomConfiguration {

    private lateinit var props: Properties

    val clientId: String by lazy { props.getProperty("oauth2.client_id", "") }

    val redirectUri: String by lazy { props.getProperty("oauth2.redirect_uri", "") }

    init {
        loadProperties()
    }

    private fun loadProperties() {

        // load from system properties
        try {
            props = System.getProperties().clone() as Properties
            try {
                val envMap = System.getenv()
                for (key in envMap.keys) {
                    props.setProperty(key, envMap[key])
                }
            } catch (ignore: SecurityException) {
            }
            normalize(props)
        } catch (ignore: SecurityException) {
            // Unsigned applets are not allowed to access System properties
            props = Properties()
        }

        @Suppress("LocalVariableName")
        val TWITTER4J_PROPERTIES = "twitter4j.properties"

        // override System properties with ./twitter4j.properties in the classpath
        loadProperties(props, "." + File.separatorChar + TWITTER4J_PROPERTIES)

        // then, override with /twitter4j.properties in the classpath
        loadProperties(
            props, twitter4j.conf.Configuration::class.java.getResourceAsStream("/$TWITTER4J_PROPERTIES")
        )

        // then, override with /WEB/INF/twitter4j.properties in the classpath
        loadProperties(
            props, twitter4j.conf.Configuration::class.java.getResourceAsStream("/WEB-INF/$TWITTER4J_PROPERTIES")
        )
    }

    private fun loadProperties(props: Properties, path: String): Boolean {
        var fis: FileInputStream? = null
        try {
            val file = File(path)
            if (file.exists() && file.isFile) {
                fis = FileInputStream(file)
                props.load(fis)
                normalize(props)
                return true
            }
        } catch (ignore: Exception) {
        } finally {
            try {
                fis?.close()
            } catch (ignore: IOException) {
            }
        }
        return false
    }

    private fun loadProperties(props: Properties, inputStream: InputStream?): Boolean {
        try {
            props.load(inputStream)
            normalize(props)
            return true
        } catch (ignore: Exception) {
        }
        return false
    }

    private fun normalize(props: Properties) {
        val toBeNormalized = ArrayList<String>(10)
        for (key in props.keys) {
            val keyStr = key as String
            if (-1 != keyStr.indexOf("twitter4j.")) {
                toBeNormalized.add(keyStr)
            }
        }
        for (keyStr in toBeNormalized) {
            val property = props.getProperty(keyStr)
            val index = keyStr.indexOf("twitter4j.")
            val newKey = keyStr.substring(0, index) + keyStr.substring(index + 10)
            props.setProperty(newKey, property)
        }
    }
}