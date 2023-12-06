# Hotel Reservation
## 요구사항
> lv1
>
> 1. 사용자가 호텔 예약을 할 수 있는 메뉴를 표시하세요. (번호는 1~6번까지 만들어봐요.)
> 2. 메뉴에서 4번을 누르면 호텔 예약 프로그램을 종료할 수 있어요
> 3. 예약 플로우는 성함을 입력받고 방 번호를 입력받고 체크인 날짜를 입력받고 체크아웃 날짜를 입력받아요
> 4. 1번을 눌러 방 예약을 받을 수 있도록 구현해 봐요
> 5. 방 번호는 100~999호실까지 존재해요
> 6. 체크인 날짜는 지금 날짜와 비교해서 이전날짜는 입력받을 수 없고 체크아웃 날짜는 체크인 날짜보다 이전이거나 같을 수는 없어요
> 7. 입력이 완료되면 임의의 금액을 지급해 주고 랜덤으로 호텔 예약비로 빠져나가도록 구현해 봐요
     
> lv2
>
> 1. 메뉴에서 2번을 눌러 호텔 예약자 목록을 보여줘요. (예시. 1. 사용자: ㅇㅇㅇㅇ, 방 번호: ㅇㅇㅇ호, 체크인: 2023-07-21. 체크아웃: 2023-08-01)
> 2. 메뉴에서 3번을 눌러 호텔 예약자 목록을 정렬 기능을 사용하여 체크인 날짜순으로 오름차순으로 정렬해 봐요
> 3. 예약 플로우를 수정해 봐요. 해당 체크인 체크아웃 날짜에 선택한 방 번호를 예약 가능한지 불가능한지 판단하게 변경해 봐요. 예약이 불가능하면 체크인, 체크아웃 날짜를 변경해서 다시 검사해 보는 플로우를 만들어봐요.

> lv3
>
> 1. 메뉴에서 5번을 눌러 조회할 사용자 이름을 입력 받아봐요
> 2. 조회가 불가능한 사용자 이름을 입력하면 "예약된 사용자를 찾을 수 없습니다." 가 출력돼요
> 3. 출력이 가능하면 입금, 출금, 내역을 확인할 수 있어요.

> lv4
>
> 1. 메뉴에서 6번을 눌러 예약에 대해 변경할 사용자 이름을 입력 받아봐요
> 2. 예약목록에 없는 이름이면 "사용자 이름으로 예약된 목록을 찾을 수 없습니다. "가 출력돼요
> 3. 예약 조회 결과가 존재하면 1번부터 해서 예약 목록을 보여주고 숫자를 입력해서 변경할 수 있도록 해봐요. (예시. 1. 방 번호: ㅇㅇㅇ호, 체크인: 2023-07-21, 체크아웃: 2023-08-01)
> 4. 1번을 누르면 변경, 2번을 누르면 취소, 이외 번호는 이전 메뉴로 돌아가도록 구현해 봐요
> 5. 변경 시에는 예약 때처럼 체크인, 체크아웃 규칙에 어긋나는지 확인해야 해요
> 6. 취소는 현재 날짜로부터 3일전이면 0원, 5일 이전이면 30%, 7일 이전이면 50%, 14일 이전이면 80%, 체크인 30일 이전이면 100% 환불되는 알고리즘을 구현해 봐요.