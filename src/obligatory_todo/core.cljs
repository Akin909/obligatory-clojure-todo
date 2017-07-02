(ns obligatory-todo.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(println "Obligatory Todo console")

;; define your app data so that it doesn't get over-written on reload

(def input-styles {:width "500px"
                   :height "40px"
                   :font-size "15px"
                   :border-radius "4px"
                   :border "1px solid grey"
                   :box-shadow "0 1px 0 rgba(0, 0, 0, 0.5)"
                   :margin "5px 0"
                   :padding "5px"})

(defonce todos (atom (map {})))
(defonce current-todo (atom ""))

(defn todo-input [placeholder]
  [:input
   {:type "text"
    :style input-styles
    :placeholder placeholder
    :value @current-todo
    :on-change #(reset! current-todo (-> % .-target .-value ))}])


(defn add-todo []
  [:div {:style {:display "flex" :flex-direction "column"}}
   [todo-input "Add A Todo"]
   [todo-input "Add A Title"]])

(defn todo-container []
  [:center
   {:style {:font-family "Helvetica"}}
   [:h1
    "Obligatory Todo"]
   [add-todo]])

(reagent/render-component [todo-container]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
(prn current-todo)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
