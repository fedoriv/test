Feature: Test gmail for login, create, draft and send Message

  Scenario Outline: Checking of draft messages
     Given Gmail start page, where we type <Login>
     Then we clic next button
     When password field appears we type there <Password>
     And after we click on next button
     When main page appears, create new message fill all fields and close window
     When message is draft, open it and send
     Examples:
        | Login                        | Password           |
        | ta.lab.testprofile@gmail.com | ownerofthisPROFILE |