# Beatrix E2E Testing

##
Here you can find the different journeys that we run in our E2E layer.

# How works
There are two different perspective, E2E and visual.

- E2E check that the functionality is working.
- Visual check that UX-UI is the expected one.

# How to execute it
Run E2E in xxxx environment
```
mvn clean integration-test -De2e.environment=xxxx
```

Run visual in xxx environment
```
mvn clean integration-test -Pvisual -De2e.environment=xxxx

```
# Run the test in your site deployed locally
Run in e2e/visual to your site deployed in your local machine
```
mvn clean integration-test -Plocal
```
#Other configurable properties 
  - If you want to run an specific feature
 ```
  -Dktn.feature.locations=features/e2e/player/Deposit.feature
 ```
 - If you want to run more tests in parallel
 ```
 -Dktn.thread.count=2 (2 tests at the same time)
 ```
  - If you want to modify the timeout per feature
 ```
   -Dktn.feature.timeouts=60 
   (feature should finished before 60 seconds, if not, cancellation exception)
 ```
 #Existing Profiles
 
 You can combine visual profile with browser profiles.
 
 - Run all features of e2e
 ```
 (no profile)
  ```
 - Run all features of visual
 ```
  -Pvisual
 ```
 - Run features in specific browsers (chrome by default). It will be run features which applies browser compatibility, it means, that firefox will not run enterprise features for instance.
 ```
  -Pfirefox
  -Psafari
  -Pandroid_S7
  -Pandroid_S5
  -Pandroid_J7
 ```