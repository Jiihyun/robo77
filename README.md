# robo77 🤖

## **목차**

- [1️⃣ 프로젝트 소개](#프로젝트-소개)
- [2️⃣ 학습 목표](#학습-목표)
- [3️⃣ 게임 규칙 설명](#게임-규칙-설명)
    - 기본 규칙
    - 카드 구성표
    - 턴 진행 규칙
    - 게임 종료 조건
- [4️⃣ 프로그램 실행 방법](#프로그램-실행-방법)
    - 콘솔 실행 방법
    - 디스코드 봇 실행 방법
- [5️⃣ 기술 스택](#기술-스택)
- [6️⃣ 실행 예시](#실행-예시)
- [7️⃣ 패키지 구조](#패키지-구조)
- [8️⃣ 개발 일지](#개발-일지)

## **프로젝트 소개**

로보77 두 플레이어가 번갈아가며 카드를 내고, 카드 숫자의 합이 **11의 배수**가 되거나 **77을 초과하지 않도록** 유지해야 하는 게임입니다.

이번 구현에서는 실제 두 명의 플레이어 대신 사용자와 로봇이 대결하는 형태로 진행됩니다.

플레이어는 숫자 카드와 특수 카드를 전략적으로 사용하여 상대방이 패배하도록 유도합니다.

이 프로젝트는 게임을 **객체지향적으로 설계한 콘솔 버전**과 **JDA 기반 디스코드 봇 버전**으로 구현하여,

소스코드를 클론하지 않아도, 프리코스 참여 인원 모두가 사용하는 디스코드 환경에서 바로 편하게 플레이할 수 있도록 구성했습니다.

---

## **학습 목표**

1–3주차 동안 쌓아 온 저만의 객체지향 기준을 더 복잡하고 현실적인 흐름 속에 직접 적용해보는 것을 목표로 했습니다.

플랫폼이 콘솔에서 디스코드로 확장되고, 구현 과정에서 게임 요구사항이 변화하는 상황 속에서

제가 세운 객체지향 기준이 언제 흔들리고 언제 빛나는지를 직접 경험하며,

**더 단단하고 유연한 객체지향 설계 기준으로 발전시키는 것**이 핵심 목표입니다.

프로젝트 기획 배경 등 더 자세한 기획 과정은 [proposal 문서](./docs/proposal.md)에서 확인하실 수 있습니다.

---

## **게임 규칙 설명**

### - 기본 규칙

| 항목         | 내용                                                           |
|------------|--------------------------------------------------------------|
| **플레이 인원** | 사용자 1명 vs 컴퓨터 1명                                             |
| **총 카드 수** | **56장**                                                      |
| **초기 패**   | 각 플레이어는 **5장씩** 배분받는다.                                       |
| **덱 관리**   | 사용하지 않은 카드들은 덱에 남으며, 카드를 낸 뒤에는 덱에서 1장씩 보충한다. (덱이 비면 보충하지 않음) |
| **시작 합계**  | 0으로 시작                                                       |
| **턴 순서**   | 사용자 → 컴퓨터 순서로 진행 (특수카드로 순서 변동 가능)                            |

---

### - 카드 구성표

| 카드                                 | 장 수  | 설명                                           |
|------------------------------------|------|----------------------------------------------|
| `0`                                | 4장   | 합계에 0을 더한다.                                  |
| `2~9`                              | 각 3장 | 해당 숫자를 합계에 더한다.                              |
| `10`                               | 8장   | 합계에 10을 더한다.                                 |
| `-10`                              | 4장   | 합계에 10을 뺀다.                                  |
| `x2`                               | 4장   | **다음 플레이어가 카드 2장을 연속으로 내야 한다.**              |
| `reverse`                          | 5장   | **턴 순서를 반전시킨다.** (2인일 경우 같은 플레이어가 한 번 더 낸다.) |
| `11`, `22`, `33`, `44`, `55`, `66` | 각 1장 | 해당 숫자를 합계에 더한다.                              |
| `76`                               | 1장   | 합계에 76을 더한다.                                 |
| **총합**                             |      | **56장 ✅**                                    |

---

### - 턴 진행 규칙

1. 자신의 차례가 되면 손패에서 카드를 1장 선택해 낸다.
2. 카드 효과를 적용하여 합계를 갱신한다.
3. 합계가 **77을 초과하거나 11의 배수(11,22,33,44,55,66,77)** 가 되면 즉시 게임 종료된다.
    - 해당 카드를 낸 플레이어가 **즉시 패배**
4. `x2` 카드가 사용된 경우, 다음 플레이어는 카드 2장을 연속으로 내야 한다.
5. `reverse` 카드가 사용된 경우, 턴 순서가 반전된다.
    - 2인 플레이에서는 같은 플레이어가 한 번 더 낸다.
6. 카드를 낸 후 덱에서 1장을 새로 뽑아 손패를 5장으로 유지한다.
    - 덱이 소진되면 추가로 뽑지 않는다.
7. 게임이 종료되면 승패 결과를 출력한다.

---

### - 게임 종료 조건

| 조건                                   | 결과           |
|--------------------------------------|--------------|
| 합계가 **77 초과**                        | 해당 턴 플레이어 패배 |
| 합계가 **11의 배수(11,22,33,44,55,66,77)** | 해당 턴 플레이어 패배 |

---

## **프로그램 실행 방법**

### - 콘솔 실행 방법

- **조건**
    - Java 버전이 21 또는 그 이상이어야 합니다.
    - Eclipse 또는 IntelliJ IDEA와 같은 IDE에서 실행하는 것을 추천합니다.
- **실행 방법**
    1. 해당 Repository를 실행 환경에서 Clone합니다.
    2. IDE에서 Clone되어 있는 폴더를 엽니다.
    3. [RoboGameApplication.java](./src/main/java/robo77/RoboGameApplication.java) 파일을 실행합니다.

### - 디스코드 봇 실행 방법

- **조건**
    - 디스코드 dm 제약 조건으로 인해 [로보77 서버](https://discord.gg/gSRBvEP9)에 가입해야 합니다.
    - 채널 내 메세지 또는 봇과의 dm을 통해 게임을 실행할 수 있습니다.
- **실행 방법**
    1. `/startgame` 명령어를 입력하여 게임을 시작합니다.
    2. `/play` 명령어를 통해 원하는 카드를 제출합니다.
    3. `/hand` 명령어를 통해 가지고 있는 카드를 확인할 수 있습니다.
    4. `/quit` 명령어를 통해 게임을 그만할 수 있습니다.
    5. `/guide` 명령어를 통해 게임 규칙을 파악합니다.

---

## **기술 스택**

- 언어: Java 21
- 빌드 도구: Gradle
- 라이브러리
    - JDA(Java Discord API): 디스코드 봇 구현 위함
    - JUnit 5: 단위 테스트 프레임워크
    - AssertJ: 테스트 가독성과 표현력 향상을 위한 어설션 라이브러리
    - Mockito: Mock 기반 테스트
    - Logback: 로깅

---

## **실행 예시**

| 콘솔                                                                                     | 디스코드                                                                                            |
|----------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------|
| ![콘솔](https://github.com/user-attachments/assets/dbc100ae-d5f9-4c2e-83c1-51a73ca2bb63) | ![디스코드봇](https://github.com/user-attachments/assets/f75f3e55-420c-453e-a691-2d53de5d4181) |

```text
로보77 게임을 시작합니다!
이름을 입력해주세요.
jihyun

당신의 손패: [10, reverse, 8, 4, 7]
제출할 카드를 입력해주세요.
10

jihyun(이)가 10카드를 냈습니다.

bot(이)가 10카드를 냈습니다.

당신의 손패: [reverse, 8, 4, 7, 4]
제출할 카드를 입력해주세요.
reverse

jihyun(이)가 reverse카드를 냈습니다.

당신의 손패: [8, 4, 7, 4, 3]
제출할 카드를 입력해주세요.                      // 순서를 뒤집었으니 카드 한번 더 제출
8

jihyun(이)가 8카드를 냈습니다.

bot(이)가 11카드를 냈습니다.

당신의 손패: [4, 7, 4, 3, 10]
제출할 카드를 입력해주세요.
5
[ERROR] 손패에 있는 카드만 제출할 수 있습니다.     // 손패에 카드 `5`를 가지고 있지 않으니 예외 발생

당신의 손패: [4, 7, 4, 3, 10]
제출할 카드를 입력해주세요.
4

jihyun(이)가 4카드를 냈습니다.

bot(이)가 10카드를 냈습니다.

당신의 손패: [7, 4, 3, 10, 8]
제출할 카드를 입력해주세요.
3

jihyun(이)가 3카드를 냈습니다.

bot(이)가 10카드를 냈습니다.

게임 종료
합계가 66이므로 jihyun의 승리입니다.
```

---

## **패키지 구조**

|      Package       |          Class          | Description                                                             |
|:------------------:|:-----------------------:|:------------------------------------------------------------------------|
|    ▶️ **root**     |  DiscordBotApplication  | Discord 봇을 실행하는 진입점                                                     |
|                    |   RoboGameApplication   | 콘솔 기반의 Robo77 게임을 실행하는 진입점                                              |
| 🕹️ **controller** |   RoboGameController    | 콘솔 기반의 Robo77 게임 흐름을 제어하는 컨트롤러                                          |
|   👾 **discord**   |         Command         | Discord에서 사용될 명령어를 정의하는 열거형 클래스                                         |
|                    |    CommandRegistrar     | Discord 봇의 명령어를 등록하는 클래스                                                |
|                    | DiscordCommandListener  | Discord 채널의 명령어 입력을 수신하는 리스너                                            |
|                    |   GameSessionManager    | Discord 채널별 게임 세션을 관리하는 클래스                                             |
|                    |     JDAInitializer      | JDA(Discord API)를 초기화하고 봇을 실행하는 클래스                                     |
|                    |  config/DiscordConfig   | Discord 봇 관련 설정을 담당하는 클래스                                               |
|   💾 **domain**    |      EndCondition       | 게임 종료 조건을 정의하는 인터페이스                                                    |
|                    |    endcondition/...     | `MultipleCondition`, `OrCondition`, `OverLimitCondition` 등 게임 종료 조건 구현체 |
|                    |        GameScore        | 게임 점수를 관리하는 클래스                                                         |
|                    |          Hand           | 플레이어가 손에 들고 있는 카드를 표현하는 클래스                                             |
|                    |         Referee         | 게임의 승패를 판정하는 심판 클래스                                                     |
|                    |        RoboGame         | Robo77 게임의 핵심 로직을 담당하는 클래스                                              |
|                    |       TurnResult        | 턴의 결과를 저장하는 클래스                                                         |
|                    |        card/Card        | 게임에 사용되는 카드를 표현하는 클래스                                                   |
|                    |   card/CardGenerator    | 게임 실행 시 필요한 카드를 생성하는 역할을 담당하는 클래스                                       |
|                    |      card/CardType      | 카드의 종류를 정의하는 열거형 클래스                                                    |
|                    |        card/Deck        | 게임에 사용될 카드 덱을 관리하는 클래스                                                  |
|                    | card/SubmitCardStrategy | 카드 제출 전략을 정의하는 인터페이스                                                    |
|                    | card/submitstrategy/... | `BotSubmitStrategy`, `HumanSubmitStrategy` 등 카드 제출 전략 구현체               |
|                    |       player/Name       | 플레이어의 이름을 표현하는 클래스                                                      |
|                    |      player/Player      | 게임 참가자를 표현하는 클래스                                                        |
|                    |     player/Players      | 게임 참가자 목록을 관리하는 일급 컬렉션                                                  |
|                    |    turn/TurnManager     | 게임의 턴 순서를 관리하는 클래스                                                      |
|                    |     turn/TurnPolicy     | 턴 진행 정책을 정의하는 인터페이스                                                     |
|                    | turn/TurnPolicyFactory  | 턴 정책 객체를 생성하는 팩토리                                                       |
|                    |   turn/turnpolicy/...   | `DoubleTurnPolicy`, `NormalTurnPolicy`, `ReverseTurnPolicy` 등 턴 정책 구현체  |
|  🚨 **exception**  |    ExceptionMessage     | 예외 메시지를 관리하는 클래스                                                        |
|    💬 **view**     |      CardRenderer       | 카드 효과를 출력할 수 있는 형태로 렌더링하는 클래스                                           |
|                    |      ConsoleInput       | 콘솔 입력을 처리하는 클래스                                                         |
|                    |  output/ConsoleOutput   | 콘솔 출력을 담당하는 클래스                                                         |
|                    |  output/DiscordOutput   | Discord로 출력을 담당하는 클래스                                                   |

```text
robo77
├── DiscordBotApplication.java
├── RoboGameApplication.java
├── controller
│   └── RoboGameController.java
├── discord
│   ├── Command.java
│   ├── CommandRegistrar.java
│   ├── DiscordCommandListener.java
│   ├── GameSessionManager.java
│   ├── JDAInitializer.java
│   └── config
│       └── DiscordConfig.java
├── domain
│   ├── EndCondition.java
│   ├── GameScore.java
│   ├── Hand.java
│   ├── Referee.java
│   ├── RoboGame.java
│   ├── TurnResult.java
│   ├── card
│   │   ├── Card.java
│   │   ├── CardGenerator.java
│   │   ├── CardType.java
│   │   ├── Deck.java
│   │   ├── SubmitCardStrategy.java
│   │   └── submitstrategy
│   │       ├── BotSubmitStrategy.java
│   │       └── HumanSubmitStrategy.java
│   ├── endcondition
│   │   ├── MultipleCondition.java
│   │   ├── OrCondition.java
│   │   └── OverLimitCondition.java
│   ├── player
│   │   ├── Name.java
│   │   ├── Player.java
│   │   └── Players.java
│   └── turn
│       ├── TurnManager.java
│       ├── TurnPolicy.java
│       ├── TurnPolicyFactory.java
│       └── turnpolicy
│           ├── DoubleTurnPolicy.java
│           ├── NormalTurnPolicy.java
│           └── ReverseTurnPolicy.java
├── exception
│   └── ExceptionMessage.java
└── view
    ├── CardRenderer.java
    ├── ConsoleInput.java
    └── output
        ├── ConsoleOutput.java
        └── DiscordOutput.java
```

---

## **개발 일지**

> 구현 과정에서의 고민, 설계 선택의 이유, 리팩터링 과정,  
> 그리고 실패와 수정의 흔적들을 꾸준히 기록한 개발 일지입니다.  
> 코드만으로 파악하기 어려운 의도와 사고 과정은 아래 블로그에서 확인하실 수 있습니다.

- 블로그 링크: https://jiihyunn.tistory.com/category/Project/Robo77
