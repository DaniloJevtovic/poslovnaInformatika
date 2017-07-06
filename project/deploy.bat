start cmd /C "title H2 Baza&java -cp "h2-1.4.193.jar" org.h2.tools.Server -web -tcp -tcpSSL -baseDir .\db\h2"
start cmd /K "title Play Aplikacija&play run"