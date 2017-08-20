# blog

Clojureで作ったブログサービス的ななにか
## Usage
現状ローカル上にサーバー立ち上げての動作確認のみ。

### 動作環境
* Ubuntu 14.04
* PostgreSQL 9.3.17
* Leiningen 2.7.1 on Java 1.8.0_101 Java HotSpot(TM) 64-Bit Server VM

### 動作手順
1. project.cljの:profiles -> :env -> :db　にあるデータベース設定を書き換える
2. setup.sqlを動かしてデータベースの初期化。PostgreSQLように書かれているので適宜書き換え
3. "lein run"を実行
