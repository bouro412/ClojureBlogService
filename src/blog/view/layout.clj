(ns blog.view.layout
  (:require [hiccup.page :refer [html5 include-css include-js]]
            [blog.util.login :refer [login-user login?]]))

(defn common [req & body]
  (html5
   [:head
    [:title "The Blog Service"]
    (include-css "/css/normalize.css"
                 "/css/papier-1.3.1.min.css"
                 "/css/style.css")
    (include-js "/js/main.js")]
   [:body
    (if-let [user (login-user req)]
      [:header.top-bar.bg-green.depth-3
       "blog "
       (:name user)
       " "
       [:a {:href "/logout"} "ログアウト"]]
      [:header.top-bar.bg-green.depth-3
       "blog "
       [:a {:href "/login"} "ログイン"]])
    [:main body]]))
