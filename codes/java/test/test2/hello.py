#!/usr/bin/python
from selenium import webdriver

br = webdriver.Chrome()
br.get("http://www.target.com")
print "hello"
