# Contributing to Spring SingleFlight

First off, thank you for considering contributing to Spring SingleFlight! It's people like you that make open source such a great community.

## Where do I go from here?

If you've noticed a bug or have a question, [search the issue tracker](#) to see if someone else in the community has already created a ticket. If not, go ahead and make one!

## Fork & create a branch

If this is something you think you can fix, then fork Spring SingleFlight and create a branch with a descriptive name.

A good branch name would be (where issue #325 is the ticket you're working on):

```
git checkout -b 325-fix-registry-timeout
```

## Get the test suite running

Make sure you have Java 17 and Maven installed.

Run the tests:
```bash
mvn clean test
```

## Implement your fix or feature

At this point, you're ready to make your changes. Feel free to ask for help; everyone is a beginner at first.

## Make a Pull Request

At this point, you should switch back to your master branch and make sure it's up to date with Spring SingleFlight's master branch:

```bash
git remote add upstream git@github.com:darshan/spring-singleflight.git
git checkout master
git pull upstream master
```

Then update your feature branch from your local copy of master, and push it!

```bash
git checkout 325-fix-registry-timeout
git rebase master
git push --set-upstream origin 325-fix-registry-timeout
```

Finally, go to GitHub and make a Pull Request!

## Keeping your Pull Request updated

If a maintainer asks you to "rebase" your PR, they're saying that a lot of code has changed, and that you need to update your branch so it's easier to merge.

## Coding Conventions

Start reading our code and you'll get the hang of it. We largely follow standard Google Java Style formatting and Spring Boot defaults. Include Javadocs on all public APIs.
