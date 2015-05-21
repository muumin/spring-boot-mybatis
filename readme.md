Spring Boot + MyBatis3
=====================================

     gradlew

http://localhost:8080/users

# flyway

起動時に自動でMigrateするがGradleで実行する場合は以下で可能。

## DB差分反映

    gradlew flywayMigrate

## DBバージョン確認

    gradlew flywayInfo
    
## エラー修復

    gradlew flywayRepair
    
## データ完全消去

    gradlew flywayClean


# 参考サイト

[flyway](http://flywaydb.org/documentation/gradle/)

[mybatis](https://mybatis.github.io/mybatis-3/ja/)

[MyBatisでjava8のjava.time.LocalDateTimeを扱う](http://qiita.com/tterasawa/items/b16bc3dbe15c5017e0fa)

[谷本 心 in せろ部屋](http://d.hatena.ne.jp/cero-t/20141212/1418339302)

# 書籍

Java Persistence with MyBatis 3
