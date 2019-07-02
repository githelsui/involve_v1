# Involve v1.0
App keeps track of community service and volunteer hours. Input your hours and record the times/dates of your service while seeing your hours all rack up. Also record your duties and positions within a program.</br>
Application utilizes google's material design.
## Download
Unfortunately, NOT available on Google Play because of the $25 cost of hosting and my stinginess.</br>
I will be making it accessible on http://slideme.org/ (because it's free!).
But you could fork it and run the APK on Android Studio's emulators.</br>

**SDK Information**<br />
**min SDK: 23**</br>
**target SDK: 28**

## App Structure

1. Main Page displays user's list of volunteering programs/activities/organizations
2. Program's page displays its own info. & user's list of events/services associated under the organization
3. Event/Service Page displays additional info (time, date, location, etc) of the event that was completed within an organization

### Main Page (Launch Page)
The main page first launches with an empty list of programs (First Image). But adding new programs creates a colorful design scheme (Second Image). </br></br>
![](launch.png)
![](launchfull.png)

### Program's Page
Page consists of a drop down view revealing the program's additional information (First Image). Adding new events creates a list view (Second Image). <br />
![](programpage.png)
![](programlist.png)
<br /><br/>
**Adding Events/Services**<br />
Adding an event to a program is as easy as it looks (First Image). No need to calculate your hours. Just input the dates and times of your event (Second Image). </br>
![](addevent.png)
![](calculatehrs.png)<br />

**Deleting a Program or Event**<br />
Delete either program or event by sliding the colorful list container from right to left.<br/>
![](delete.gif)

### Event's Page
Page consists of all additional info. and the ability to edit the textviews. </br>
![](eventpage.png)
