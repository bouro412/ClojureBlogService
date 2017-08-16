(ns blog.view.util)

(defn error-messages [req]
  (when-let [errors (:errors req)]
    [:ul
     (for [[k v] errors
           msg v]
       [:li.error-message msg])]))
