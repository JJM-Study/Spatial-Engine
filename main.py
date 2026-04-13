from fastapi import FastAPI
from pydantic import BaseModel
from rtree import index
import math

app = FastAPI()


@app.get("/")
async def root():
    return {"message": "Hello World"}


@app.get("/temp/{name}")
async def say_hello(name: str):
    return {"message": f"Hello {name}"}


class Point(BaseModel):
    id: int
    lat: float
    lon: float

class DistanceRequest(BaseModel):
    lat1: float
    lon1: float
    lat2: float
    lon2: float

def calculate_haversine(lat1, lon1, lat2, lon2):
    # 지구 반지름 (미터 단위)
    r = 6371000

    # 라디안 변환
    phi1, phi2 = math.radians(lat1), math.radians(lat2)
    dphi = math.radians(lon2 - lon1)
    dlambda = math.radians(lon2 - lon1)

    # 하버사인 공식 (LaTeX 수식 참고)
    a = math.sin(dphi / 2)**2 + \
        math.cos(phi1) * math.cos(phi2) * \
        math.sin(dlambda / 2)**2

    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    return r * c



# 파이썬은 내부적으로 쿼리스트링으로 변환해줘서 get도 먹히긴 한다고 함. 하지만 실제 길이 제한이 있으므로 주의.
@app.post("/distance")
async def get_distance(data: DistanceRequest):

    distance = calculate_haversine(data.lat1, data.lon1, data.lat2, data.lon2)
    return {
        "origin": {"lat": data.lat1, "lon": data.lon1},
        "destination": {"lat": data.lat2, "lon": data.lon2},
        "distance_meter": round(distance, 2),
        "distance_km": round(distance / 1000, 2)
    }


