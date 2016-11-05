Spring Boot + MyBatis3
=====================================

## 概要

* SpringBootでRESTを作成
* レガシーなDBとの戦に備えて
    * ORマッパーにMyBatis
    * DBマイグレーションにFlyway
* テスト時にSpringBootを起動してRESTをテスト
    * テスト実行毎にDBをFlywayで初期化

## library

* SpringBoot
* MyBatis
* Lombok
* Flyway
* spock + groovy
* jacoco

## 起動

     gradlew bootRun

http://localhost:8080/users/

POST/GET/DELETE/PUTでCRUD操作が可能。

## テスト

    gradlew

build/reports配下にテスト結果が出力される(でもカバレッジは100%じゃないっす)

## war作成

    gradlew war

## deploy

Tomcatに「{{host name}}/example」としてデプロイされる。

    gradle war deploy

# flywayコマンド

## DB差分反映

    gradlew flywayMigrate

## DBバージョン確認

    gradlew flywayInfo

## エラー修復

    gradlew flywayRepair

## データ完全消去

    gradlew flywayClean

# Windowsでのテスト失敗

gradlew.batのDEFAULT\_JVM\_OPTSを以下にする

    set DEFAULT_JVM_OPTS="-Dfile.encoding=UTF-8"

# 参考サイト

[flyway](http://flywaydb.org/documentation/gradle/)

[mybatis](https://mybatis.github.io/mybatis-3/ja/)

[MyBatisでjava8のjava.time.LocalDateTimeを扱う](http://qiita.com/tterasawa/items/b16bc3dbe15c5017e0fa)

[谷本 心 in せろ部屋](http://d.hatena.ne.jp/cero-t/20141212/1418339302)

# 書籍

[Java Persistence with MyBatis 3](http://www.amazon.co.jp/dp/B00DIY89P8)

[はじめてのSpring Boot](http://www.amazon.co.jp/dp/4777518655)
