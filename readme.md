Spring Boot + MyBatis3
=====================================

     gradlew bootRun

http://localhost:8080/users/

POST/GET/DELETE/PUTでCRUD操作が可能。

# テスト

    gradlew 
  
build/reports配下にテスト結果が出力される
  
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

# Windowsでのテスト失敗

gradlew.batのDEFAULT_JVM_OPTSを以下にする

    set DEFAULT_JVM_OPTS="-Dfile.encoding=UTF-8"

# 参考サイト

[flyway](http://flywaydb.org/documentation/gradle/)

[mybatis](https://mybatis.github.io/mybatis-3/ja/)

[MyBatisでjava8のjava.time.LocalDateTimeを扱う](http://qiita.com/tterasawa/items/b16bc3dbe15c5017e0fa)

[谷本 心 in せろ部屋](http://d.hatena.ne.jp/cero-t/20141212/1418339302)

# 書籍

Java Persistence with MyBatis 3
