package np.com.sanjaygubaju.revolut.utils

fun isOnline(): Boolean {
    return try {
        // Ping cloudflare
        val command = "ping -c 1 1.1.1.1"
        return Runtime.getRuntime().exec(command).waitFor() == 0
    } catch (e: Exception) {
        false
    }
}