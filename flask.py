from flask import Flask, request, jsonify
import json
app = Flask(__name__)
@app.route("/")
def main():
    #f=open("t.txt")
    #txt=f.read()
    #return txt
    import mysql.connector

    cnx = mysql.connector.connect(user='root', password='',
                                      host='127.0.0.1',
                                                                    database='air_quality')
    cursor = cnx.cursor()
    query = ("SELECT coords.x,coords.y,pollution.pollutant,pollution.numeric_val,pollution.country_c,S02.pollutant1,S02.numeric_val1,pollution.station_name FROM coords ,pollution,S02 WHERE coords.id =pollution.id AND coords.x =S02.x AND coords.y=S02.y  ")
    data=cursor.execute(query)
    data = cursor.fetchall()
    print len(data)     	
    tmp="["
    fake="{\"y\": \" \" ,\"x\": \" \",\"pollutant\":\"  \",\"numeric_val\":\" \",\"country_c\":\" \",\"pollutant1\":\" \",\"numeric_val1\":\" \",\"station_name\":\" \"}"
    for row in data:       
       if data[0]:      
	  tmp+="{\"y\":\""+row[0] +"\" ,\"x\":\""+row[1] +"\" ,\"pollutant\":\""+row[2] +"\",\"numeric_val\":\" "+ row[3] +"\",\"country_c\":\" "+row[4] +"\",\"pollutant1\":\" "+row[5] +"\",\"numeric_val1\":\" "+row[6] +"\",\"station_name\":\" "+row[7] +"\" }," 
     
    return tmp+fake+"]"	
     


 










  
  #  cnn.execute(sql)
   # rows = cnn.fetchall()
    #column = [t[0] for t in cnn.description]
    #for row in rows:
     #   myjson = {column[0]: row[0],column[1]: row[1], column[3]: row[3], column[4]: row[4], column[5]: row[5],column[6]: row[6],column[7]: row[7]}
      #  myresult = json.dumps(myjson, indent=3)
    #return myresult
    
app.run(threaded=True)


