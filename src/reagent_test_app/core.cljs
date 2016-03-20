(ns reagent-test-app.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(defonce app-state (atom {:new-post ""
                          :posts [{:id 0
                                   :name "Thor Amorim"
                                   :postedAt "01/01/2016"
                                   :post "Olar Mundor"
                                   :likes 100}
                                  {:id 1
                                   :name "Thor Amorim"
                                   :postedAt "02/01/2016"
                                   :post "Olar"
                                   :likes 50}
                                  {:id 2
                                   :name "Thor Amorim"
                                   :postedAt "03/01/2016"
                                   :post "Mundor"
                                   :likes 25}]}))

(defn handle-click [id]
  (swap! app-state update-in [:posts id :likes] inc))

(defn handle-submit [e]
  (.preventDefault e)
  (swap! app-state update :posts conj {:id (count (:posts @app-state))
                                       :name "Thor Amorim"
                                       :postedAt "04/01/2016"
                                       :post (:new-post @app-state)
                                       :likes 0})
  (swap! app-state assoc :new-post ""))

(defn handle-change [e]
  (swap! app-state assoc :new-post (.-target.value e)))

(defn test-header []
  [:div.row
   [:div.col-lg-12.text-center
    {:style {:background "black" :color "white"}}
    [:h2 {:style {:margin "10px 0"}} "Header"]]])

(defn test-input [new-post]
  [:div.row {:style {:margin-top "24px"}}
   [:div.col-lg-8.col-lg-offset-2
    [:form {:on-submit handle-submit}
     [:input.form-control
      {:type "text"
       :value new-post
       :auto-focus true
       :on-change handle-change}]]]])

(defn test-post [post]
  [:div
   [:div.row
    [:div.col-lg-8.col-lg-offset-2
     [:hr]]]
   [:div.row
    [:div.col-lg-4.col-lg-offset-2
     [:h2 (:name post)]]
    [:div.col-lg-4
     [:span.pull-right
      {:style {:color "grey" :margin-top "20px" :font-size "16px"}}
      (:postedAt post)]]]
   [:div.row
    [:div.col-lg-8.col-lg-offset-2
     [:br]
     [:p {:style {:font-size "18px"}} (:post post)]]]
   [:div.row
    [:div.col-lg-8.col-lg-offset-2
     [:a.pull-right
      {:on-click #(handle-click (:id post))
       :style {:font-size "16px" :cursor "pointer"}}
      (:likes post)]]]])

(defn test-post-list [posts]
  [:div {:style {:margin-top "12px"}}
   (for [post posts]
     [:div {:key (:id post)}
      [test-post post]])])

(defn test-app []
  [:div.container-fluid
   [test-header]
   [test-input (:new-post @app-state)]
   [test-post-list (reverse (:posts @app-state))]])

(reagent/render-component [test-app]
                          (. js/document (getElementById "app")))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
