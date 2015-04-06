# Social Lunch
Social Lunch is an Android app that matches hungry people, who are nearby and have similar food preference, to meet for lunch together.

## User stories
The user stories are divided into 3 main sections:

1. Onboarding
2. Discovery
3. Communication

### 1. Onboarding
#### Compulsory
* Allow user to join or login without friction
* Allow user to set up his/her profile so that he/she can be identified by other users with a user name and profile image

#### Optional
* Allow user to provide additional information in profile setup, e.g. 
  + dietary preference, 
  + age group, 
  + gender, 
  + profession, 
  + company, etc.
* Social login via
  + Facebook
    - For revealing common friends of 2 users
  + Twitter
    - Facilitate sharing suggestion via tweet
  + LinkedIn
    - Facilitate filtering of users by company and profession

### 2. Discovery
#### Compulsory
* List suggestions, which are nearby venues suggested by users along with meeting time
* Create a suggestion
  + Search for nearby venues 
    - via integration with Yelp API
  + Suggest meeting time
* Join a suggestion

#### Optional
* Support filtering of suggestions
  + venue name
  + categories
  + meeting time 
* Display reviews of restaurants 
  + via integration with Yelp API
* History of past matches
* Add restaurants as favorites
* Follow other users
* Review other users

### 3. Communication
#### Compulsory
* Remote push notification to notify that someone else is interested to go for lunch with user.
* Remote push notification to notify change of hungry status
* Remote push notification to notify change of departure / arrival
* Allow matched users to message each other.
  + Similar to messaging feature in mobile app of AirBnB or TaskRabbit.
  + Could be implemented using https://www.firebase.com/ or http://www.pubnub.com/


#### Optional
* Send email with deep linking to notify user that he/she has received a message.
* Allow user to set an alarm so that they will receive a lunch reminder via local push notification.
* Address Book Spam.. Um.. Sharing. 

## Wireframes
You may check out the wireframes at https://thelunchapp.mybalsamiq.com/projects/wires/Social%20Lunch

## Demo

![Alt text](/gifs/Cap.gif?raw=true "Demo")

