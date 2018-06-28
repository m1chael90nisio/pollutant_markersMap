#!/usr/bin/env python
from StringIO import StringIO
import urllib
import urllib2
import datetime
from datetime import datetime, date, timedelta

days = (date.today() - timedelta(days=1)).strftime("%Y-%m-%d")


url = 'http://fme.discomap.eea.europa.eu/fmedatastreaming/AirQuality/AirQualityUTDExport.fmw'
print days


data="POSTDATA=FromDate="+days+"&ToDate="+days+"&Countrycode=es&InsertedSinceDate=&UpdatedSinceDate=&Pollutant=PM10&Namespace=&Format=XML&Qoute_output_values=No&UserToken=*********"  

req = urllib2.Request(url, data)
response = urllib2.urlopen(req)
the_page = response.read()
print the_page







