import csv
from itertools import islice
from time import sleep
import json

from pykafka import KafkaClient



client_SD = KafkaClient(hosts='192.168.160.18:19092')
topic_SD = client_SD.topics['ESP33_SensorData']
producer_SD = topic_SD.get_sync_producer()


data = {"firefighters":[ dict() ,  dict() ,  dict() ]}
c = 0
env_idx = gps_idx = hr_idx = 1
env_period = 60 / 6
gps_period = 60 / 10
hr_period = 60 / 60

while c < 160:
    # ENV DATA #
    if c % env_period == 0:
        with open('a1_env.csv') as csv_file:
            row = next(csv.reader(islice(csv_file, env_idx, env_idx+1)))
            data["firefighters"][0].update({"CO": row[1], "temp": row[2], "hum": row[6], "bat": row[8]})
        with open('a2_env.csv') as csv_file:
            row = next(csv.reader(islice(csv_file, env_idx, env_idx+1)))
            data["firefighters"][1].update({"CO": row[1], "temp": row[2], "hum": row[6], "bat": row[8]})
        with open('vr12_env.csv') as csv_file:
            row = next(csv.reader(islice(csv_file, env_idx, env_idx+1)))
            data["firefighters"][2].update({"CO": row[1], "temp": row[2], "hum": row[6], "bat": row[8]})
        env_idx += 1
    # GPS DATA #
    if c % gps_period == 0:
        with open('a1_gps.csv') as csv_file:
            row = next(csv.reader(islice(csv_file, gps_idx, gps_idx + 1)))
            data["firefighters"][0].update({"lat": row[1], "longi": row[2], "alt": row[4]})
        with open('a2_gps.csv') as csv_file:
            row = next(csv.reader(islice(csv_file, gps_idx, gps_idx + 1)))
            data["firefighters"][1].update({"lat": row[1], "longi": row[2], "alt": row[4]})
        with open('vr12_gps.csv') as csv_file:
            row = next(csv.reader(islice(csv_file, gps_idx, gps_idx + 1)))
            data["firefighters"][2].update({"lat": row[1], "longi": row[2], "alt": row[4]})
        gps_idx += 1
    # HR DATA #
    if c % hr_period == 0:
        with open('a1_hr.csv') as csv_file:
            row = next(csv.reader(islice(csv_file, hr_idx, hr_idx + 1)))
            data["firefighters"][0].update({"hr": row[1]})
        with open('a2_hr.csv') as csv_file:
            row = next(csv.reader(islice(csv_file, hr_idx, hr_idx + 1)))
            data["firefighters"][1].update({"hr": row[1]})
        with open('vr12_hr.csv') as csv_file:
            row = next(csv.reader(islice(csv_file, hr_idx, hr_idx + 1)))
            data["firefighters"][2].update({"hr": row[1]})
        hr_idx += 1
    print(data)
    producer_SD.produce(json.dumps(data).encode())
    sleep(5)
    c += 1
producer_SD.stop()
