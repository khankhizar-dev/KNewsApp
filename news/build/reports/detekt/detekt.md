# detekt

## Metrics

* 86 number of properties

* 58 number of functions

* 17 number of classes

* 13 number of packages

* 20 number of kt files

## Complexity Report

* 1,642 lines of code (loc)

* 1,471 source lines of code (sloc)

* 1,011 logical lines of code (lloc)

* 7 comment lines of code (cloc)

* 100 cyclomatic complexity (mcc)

* 90 cognitive complexity

* 1 number of total code smells

* 0% comment source ratio

* 98 mcc per 1,000 lloc

* 0 code smells per 1,000 lloc

## Findings (1)

### style, MagicNumber (1)

Report magic numbers. Magic number is a numeric literal that is not defined as a constant and hence it's unclear what the purpose of this number is. It's better to declare such numbers as constants and give them a proper name. By default, -1, 0, 1, and 2 are not considered to be magic numbers.

[Documentation](https://detekt.dev/docs/rules/style#magicnumber)

* D:/KNewsApp/news/src/test/java/com/android/knewsapp/news/data/local/NewsMigrationTest.kt:36:49
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
33             close()
34         }
35 
36         helper.runMigrationsAndValidate(testDb, 3, true)
!!                                                 ^ error
37     }
38 }
39 

```

generated with [detekt version 1.23.7](https://detekt.dev/) on 2026-07-25 13:04:38 UTC
