class MyClass {
    private val myLock = java.lang.Object()
    var shouldWait: Boolean = true

    fun foo() {
        synchronized(myLock) {
            while (shouldWait) {
                try {
                    myLock.wait()
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                    return
                }
            }
            // Do some stuff here
            myLock.notifyAll()
        }
    }
}
