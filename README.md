# Quizatron
[![Build Status](https://travis-ci.com/2DSharp/Quizatron.svg?branch=master)](https://travis-ci.com/2DSharp/Quizatron)

A cross platfrom Quiz presentation application designed to run on long quiz shows that require a dedicated display screen and a console.

**Console:**
![Quizatron Console](https://i.ibb.co/wQV2qcj/qtron.png)

**Question display example:**
![Quizatron Question Display Example](https://i.ibb.co/q0PvVzm/qtron-question-Display.png)
## Motivation
Designing and running quizzes in powerpoint is a pain and confusing. Especially because of the lack of conditional manipulation of the quiz states and the ability to take control of the quiz.
Quizatron was built specifically for running long quiz shows in front of a large audience without having to display the file explorer and run how a quiz program should run. Equipped with a media player, a score board display and 5 quiz round types,
Quizatron delivers a perfect quiz show and a console to have full control of the program, even if something goes wrong.


**Quizatron** has been used to run [Inquizzitive 15](http://facebook.com/inquizzitive15/) flawlessly. 

## Getting started

### Pre-requisites

- [Oracle Java](https://www.oracle.com/technetwork/java/javase/downloads/index.html) 8 or higher
- Quizatron is a maven project. To build from source download [maven](https://maven.apache.org/).


### Installation

Written in Java, Quizatron can run everywhere with a JVM. The easiest way to get started is by downloading a packaged binary.
Binaries are available in the [binaries directory](https://github.com/2DSharp/Quizatron/tree/master/binaries).

To build the project from source:

```
git clone https://github.com/2DSharp/Quizatron.git
cd Quizatron
mvn package
java -jar target/Quizatron-1.0-SNAPSHOT.jar 
```

### Running a Quiz

You need to feed a configuration file to run a quiz. Details will be published soon.

## Support

If you are having trouble running Quizatron, send a mail at 2D@twodee.me.

## Contributors

To contribute to Quizatron, fork the repo, create a new branch and send us a pull request. Make sure you read the [CONTRIBUTING.md](https://github.com/2DSharp/Quizatron/blob/master/CONTRIBUTING.md) before sending us PRs. We welcome all sorts of contribution ranging from documentation to bug fixes. If you can find any bugs or want us to implement a feature, raise an issue. 

## License

Quizatron is licensed under GNU GPL v3.0. Read the LICENSE file for more information.
