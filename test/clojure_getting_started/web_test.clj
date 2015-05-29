(ns clojure-getting-started.web-test
  (:require [clojure.test :refer :all]
            [clojure-getting-started.web :refer :all]))
            
(is (= {} (normalize {})))
(is (= [] (normalize [])))

; TODO weeeee tests! what to do if not seq/map passed in?
(is (thrown? Exception (normalize 1)) "At least for now, throws IllegalArgumentException")
(is (thrown? Exception (normalize "abcd")) "At least for now, throws ClassCastException")


(is (= [0 1/4 1/2 3/4 1]
       (normalize '(2 3 4 5 6)))
    "Test sequence with default 1 to 0")
(is (= [0 1/3 2/3 1]
       (normalize [3 4 5 6]))
    "Test vector with default 1 to 0")

 
(is (= {"abe" [8/19 11/38 4/19 7/38 2/19 1/19] "ben" [15/38 6/19 6/19 3/19 1/38 1/19] "carl" [1 2/19 3/38 1/19 1/38 0]}
       (normalize {"abe" [10 5 2 1 -2 -4] "ben" [9 6 6 0 -5 -4] "carl" [32 -2 -3 -4 -5 -6]})) 
    "Test map with default 1 to 0")

(is (= {"abe"  [77.84210526315789 67.57894736842105 61.421052631578945 59.36842105263158 53.21052631578947 49.10526315789474]
        "ben"  [75.78947368421053 69.63157894736842 69.63157894736842 57.31578947368421 47.05263157894737 49.10526315789474]
        "carl" [123.0 53.21052631578947 51.1578947368421 49.10526315789474 47.05263157894737 45.0]}
       (normalize {"abe" [10 5 2 1 -2 -4] "ben" [9 6 6 0 -5 -4] "carl" [32 -2 -3 -4 -5 -6]} 123.0 45))
    "Test map with some other crazy normals, 123.0 to 45")

