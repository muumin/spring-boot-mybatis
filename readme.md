Spring Boot + MyBatis3
=====================================

# Azure

Azureの Application Insights にログを出力しようと試みた。

だが現状出力されない。。。。

[Java Web プロジェクトで Application Insights を使う](https://azure.microsoft.com/ja-jp/documentation/articles/app-insights-java-get-started/)

[Application Insights を使用した Java トレース ログの探索](https://azure.microsoft.com/ja-jp/documentation/articles/app-insights-java-trace-logs/)

## Gitを利用したwarのデプロイ手順

1. リソースグループを選択
1. 追加を選択
1. 項目を入力する
    1. リソースグループ名(適当)
    1. サブスクリプション(そのまま)
    1. リソースグループの場所(東日本)
1. ポータル > App Service > 追加
1. Web Appを選択して作成する
1. 項目を入力する
    1. アプリ名(適当)
    1. リソースグループ(先に作成したリソースグループ)
    1. プラン/場所(新規作成)
        1. App Serviceプラン(適当)
        1. 場所(japan east)
        1. 価格レベル(安いプランはB1)
    1. App Inights(オン)

1. 作成したApplication Insightsを開く
    1. プロパティにインストルメンテーション キーがあるのでコピーする
    1. src/main/resources/ApplicationInsights.xmlのキーを書き換える
    1. gradle clean warでwarファイルを作成する

1. 作成したWeb Appを選択
1. アプリケーション設定を開く
    1. javaバージョン(java8)
    1. プラットフォーム(64ビット)
    1. 保存は下では無く左上にあるので注意
1. デプロイオプションを選択
1. ソースをローカルGitリポジトリを選択
1. デプロイ資格情報で適当なID/パスワードで作成
    1. 保存は下では無く左上にあるので注意
1. プロパティを開く
1. Git URLがあるのでclone
1. build/libs/spring-boot-mybatis.warをcloneしたリポジトリのwebapps/ROOT.warとしてコピー
1. commit & push
1. http://{{ アプリ名 }}}.azurewebsites.net/users/ にアクセスする

# 概要

* SpringBootでRESTを作成
* レガシーなDBとの戦に備えて
    * ORマッパーにMyBatis
    * DBマイグレーションにFlyway
* テスト時にSpringBootを起動してRESTをテスト
    * テスト実行毎にDBをFlywayで初期化

# library

* SpringBoot
* MyBatis
* Lombok
* Flyway
* spock + groovy
* jacoco

# 起動

     gradlew bootRun

http://localhost:8080/users/

POST/GET/DELETE/PUTでCRUD操作が可能。

# テスト

    gradlew

build/reports配下にテスト結果が出力される(でもカバレッジは100%じゃないっす)

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

gradlew.batのDEFAULT_JVM_OPTSを以下にする

    set DEFAULT_JVM_OPTS="-Dfile.encoding=UTF-8"

# 参考サイト

[flyway](http://flywaydb.org/documentation/gradle/)

[mybatis](https://mybatis.github.io/mybatis-3/ja/)

[MyBatisでjava8のjava.time.LocalDateTimeを扱う](http://qiita.com/tterasawa/items/b16bc3dbe15c5017e0fa)

[谷本 心 in せろ部屋](http://d.hatena.ne.jp/cero-t/20141212/1418339302)

# 書籍

[Java Persistence with MyBatis 3](http://www.amazon.co.jp/dp/B00DIY89P8)

[はじめてのSpring Boot](http://www.amazon.co.jp/dp/4777518655)
