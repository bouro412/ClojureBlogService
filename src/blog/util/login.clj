(ns blog.util.login)

(defn login-user [req]
  (get-in req [:session :login-user]))

(defn login? [req & {:as args}]
  (and (login-user req)
       (every? (fn [[k v]]
                 (= v (get (login-user req) k)))
               args)))

(defn update-user [req user]
  (assoc-in req [:session :login-user] user))

