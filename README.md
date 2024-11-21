# Prof-ai-ler-test-tasks

## Task 1

Deficiencies in the Java Code:

1. Incorrect usage of `String` as a lock object:
    * `myLock` string is created as a new object, that helps to avoid 
      situations where variable is from constant pool, but it still can be 
      modified outside of the class.
    * For improving that, `myLock` should be made `final` and `private` object to avoid 
      any untrusted code from accessing it.
2. `while` loop instead of `if`:
    * Method `wait()` usually should be inside of a loop, because it help so secure code
      from cases, where occurs spurious wakeup, while the actual condition is not occurred.
    * For improving that, the string with `if (condition)` should be replaced with `while (condition)`

Potential deficiencies in the Java Code:

1. Potential deadlock risk with `wait()` and `notify()/notifyAll()`:
    * If methods `notify()/notifyAll()` will never be called, there will be deadlock.
      For avoiding that, can be used timeout.
2. try block:
    * In work with synchronized code there can occur `InterruptedException`. It can be caught,
      if call of the `wait()` method will be inside of the try block with proper catch block.

Implementation of fixed code you can find by path:
`src/main/kotlin/com/github/alpdk/profailertesttasks/Task1/task1.kt`

## Task 2

