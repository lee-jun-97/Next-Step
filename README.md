# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 



### 요구사항 0 - 배포 서버 생성 및 환경 구축 ( 2022. 12. 22 )

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답 ( 2022. 12. 23 )
* 원하는 html 파일을 보여주기 위해서는 Byte 배열로 읽어 DataOutputStream에 write 해주어야 한다.
* write 한 데이터는 DataOutputStream.flush() method 를 활용하여 출력한다.
* flush() method는 close() method를 호출하므로 추가적인 close() method 호출이 불필요하다.
* 하나의 웹 페이지를 사용자에게 정상적으로 전달하고자 할 때는 클라이언트와 서버 간의 여러 번의 요청과 응답을 주고 받는다. ( 2023. 01. 10 )

### 요구사항 2 - get 방식으로 회원가입 ( 2022. 12. 23 )
* QueryString Parsing 하는 작업 중 HttpRequestUtil Class 내의 parseQueryString method 활용
* parseQueryString method는 parseValue method를 사용하용 하였는데 return 값이 Map 이었다.
* Arrays.stream 을 사용하여 Map 에 저장하도록 구현 되어있었으나 stream 과 그에 따른 체인 메서드를 알지 못해 이해 하지 못 했다.
* stream 과 filter에 대한 추가적인 학습이 필요하다.

### 요구사항 3 - post 방식으로 회원가입 ( 2022. 12. 24 )
* POST 방식으로 요청을 받으면 GET 방식에서 URL 뒤에 붙어 오던 QueryString이 Body 부에 전달되어 옴.
* POST 방식으로 회원가입 진행할 시 마지막 Header 값까지 다 읽어야 하므로 위 요구사항들은 첫 줄만 읽고 처리하도록 변경. ( 반복문 사용 x )
* 왜 요청이 3번 씩 들어오는 가.

### 요구사항 4 - redirect 방식으로 이동 ( 2022. 12. 24 )
* redirect 방식은 Header 값에 302 Found 를 명시하고 Location: ~~ 으로 redirect 주소를 주어야 함.
* 단지 redirect 만을 진행하기에 Body 부분은 정말 필요없는 걸까 ?

### 요구사항 5 - cookie ( 2022. 12. 27 )
* HTTP Header에 Cookie 값을 logined=true/false 값으로 지정해 주어 Cookie 값에 따라 list 조회 기능 구현
* Cookie는 해당 ID에만 적용이 되는 것일까 브라우저에 적용되는 것일까
* 웹 어플리케이션 동작 원리 추가 학습이 필요하다. ( Cookie 적용할 시 어떻게 동작하는 지. )

### 요구사항 6 - stylesheet 적용 ( 2022 .12. 27 )
* Content-Type: text/css 로도 적용하여 보고 4장의 코드 참고해 Css Header 추가 하여 적용하려 해보았지만 실패....
* 코드 리팩토링 하여 Header 부분 모두 다 읽으면서 css 파일 요청이 왔을 때 응답 주는 것으로 수정 하였지망 여전히 실패 ... ( 2023. 01. 10 )
* 바보같이 css 파일 잘 읽어 놓고 response 할 때 body를 담지 않은 채로 보내고 있어서 적용이 안 되었었던 것이다... ( 2023. 01. 13 )

### heroku 서버에 배포 후
* 