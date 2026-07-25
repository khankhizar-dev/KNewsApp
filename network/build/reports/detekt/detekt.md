# detekt

## Metrics

* 24 number of properties

* 18 number of functions

* 10 number of classes

* 3 number of packages

* 6 number of kt files

## Complexity Report

* 312 lines of code (loc)

* 251 source lines of code (sloc)

* 165 logical lines of code (lloc)

* 3 comment lines of code (cloc)

* 16 cyclomatic complexity (mcc)

* 1 cognitive complexity

* 1 number of total code smells

* 1% comment source ratio

* 96 mcc per 1,000 lloc

* 6 code smells per 1,000 lloc

## Findings (1)

### style, MagicNumber (1)

Report magic numbers. Magic number is a numeric literal that is not defined as a constant and hence it's unclear what the purpose of this number is. It's better to declare such numbers as constants and give them a proper name. By default, -1, 0, 1, and 2 are not considered to be magic numbers.

[Documentation](https://detekt.dev/docs/rules/style#magicnumber)

* D:/KNewsApp/network/src/test/java/com/android/knewsapp/network/connectivity/NetworkConnectivityObserverTest.kt:43:44
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
40                 callback.onLost(mockk())
41                 assert(awaitItem() == ConnectivityObserver.Status.Lost)
42 
43                 callback.onLosing(mockk(), 100)
!!                                            ^ error
44                 assert(awaitItem() == ConnectivityObserver.Status.Losing)
45 
46                 callback.onUnavailable()

```

generated with [detekt version 1.23.7](https://detekt.dev/) on 2026-07-25 15:30:11 UTC
