<h1>Hexagonal Architecture(육각형 설계)</h1>

---

<img width="5521" height="3048" alt="image" src="https://github.com/user-attachments/assets/7bd599e5-e51c-465a-a627-bf7c1932a71c" />


[<img width="5521" height="3048" alt="image" src="https://github.com/JJM-Study/jjm/blob/cb73d400b29155f539d6337cccfde7e1bbfb1f8b/assets/Spatial%20Engine/Graph.png" />](https://github.com/JJM-Study/jjm/blob/cc1dc6b40edf0c5cad4ff3cfdfe828d55c67e8c8/assets/Spatial_Engine/Graph.png)

1. 정점 집합의 레이어 분할과 채색 규칙
 

 

서로소 (독립 된 기능을 갖는) 정점 집합의 분할 (단일 책임 원칙)

 

=>

 

V_infrastructure = { NearbyController, FastAPIAdapter, LocalEngineAdapter }

V_core = { SpatialEngineService }

V_engine = { LocalSptialEngine, FastAPIEngine }

V_interface = { SpatialEngineUseCase, SpatialEnginePort }

 

=>

 

V = V_Infrastructre ∪ V_core ∪ V_interface ∪ V_engine (V_i ∩ v_j = ∅, i ≠ j )

 

레이어별 라벨링은

 

ｆ:{V_Infrastructre, V_core, V_Interface, V_engine } → {Red, Blue, Green, Yellow}

 

 

허용 제약(Allowed Dependencies)

 

Infrastructure에서 Intraface로의 호출 관계 :

 

e = (u, v) ∈ E where u ∈ V_infra, v ∈ V_interface

 

Core에서 Interface로의 호출 관계 :

 

e = (u, v) ∈ E where u ∈ V_core, v ∈ V_interface

 

금지 제약(Forbidden Dependencies)

 

Core에서 Infrastructure 또는 Engine으로의 직접 의존 관계 :

 

e = (u, v) not ∈ E where u ∈V_Core, v ∈ (V_Infrastructure )

 

 

2. 도달 가능성을 토한 의존성 오염 방지 증명
 

순수 JVM 소스코드로만 작성하여 외부 프레임워크에 종속 됐는지, 아닌지를 기준으로 집합 V_ext, V_in 으로 분류.


{ Fast API Spatial Engine, Local Spatial Engine} ∈ V_Engine

{Controller, FastAPI Engine Adapter, Local Engine Adapter} ∈ V_Infra


{Service} ∈ V_Core

{UseCase, Port} ∈ V_Interface


V_ext = V_Engine ∪ V_Infra

V_in = V_Core ∪ V_Interface


성립.

여기에서,

∀_u ∈ V_in, ∀_v ∈ V_ext,￢(u ⇝ v) 성립해야 함.


 

도달 가능성 증명 ==>

 

u = UseCase: 진출 간선 없음 ⇒ R(UseCase) = {UseCase}

 

u = Service: 진출 간선 {Service, UseCase}, {Service, Port} ⇒  R(Service) = {Service, UseCase, Port}

 

u = Port : 진출 간선 없음 ⇒ R{Port} ⇒ {Port}

 

이에 따라서,

 

모든 V_in 내부 모든 노드에서 출발하는 도달 가능 집합의 합집합은

 

U{u_∈v_in} R(u) = V_in,

 

V_in ∩ V_ex = ∅

 

이므로

 

∀_u ∈ V_in, ∀_v ∈ V_ext, ￢(u ⇝ v) 성립 즉, 오염 없음 증명.

 

= 유효성 검증 중 발견한 오류 사항 =

 

이상으로 유효성 검증 중, Service 등 내부 로직 집합에서 '

import reactor.core.publisher.Mono;
@Service
 

등 오염원이 존재했음을 확인했다. 내외부의 기준은 JVM 내의 순수 소스코드로 실행이 되냐, 아니냐의 판별 기준. 이에 따라 좀 더 검토하고 코드 수정 사항으로서 고려할 필요가 있을 것 같다.

 


 

또한, 그래프에서, Service의 간선을 u = port : 진출 간선 {port, service}  ⇒  R{port} = {port, service}  로서 잘못 표기했음을 확인, u = port : 진출 간선 없음  ⇒   R{port} = {port}로 맞게 수정함.

 

더하여 ArchUnit 라는 단위 테스트 라이브러리를 활용하면, 순환 참조의 문제가 발생하는지 등 쉽게 알 수 있는 것 같으니 이에 대해서도 알아보자. 
