# DBProject2022 ( FITNESS CENTER TOTAL MANAGEMENT )

### `Gym`
|__ `G_selectMenu.java`   :  Menu page for gym owner  
|__ `G_changePriceInformation.java` : change price information  
|__ `G_changePromotionInformation.java` :  change promotion information  
|__ `G_showTrainers.java` : show trainer information (name, work time)  
|__ `G_countTrainees.java`  : show number of members enrolled      

### `TrainerWithGUI`  
|__ `TrainerMenuJDBC.java`   :  JDBC related functions for the trainer query  
|__ `TrainerJDialogGUI.java` :  GUI files for the trainer menu page   
|__ `TrainerMenuJTable.java` :  Trainer Menu Files and connecting to the sql functions  

  
### `MemberInfo`
|__ `M_MainScreen.java` : Menu page for member    
|__ `M_myPage.java` : Check the account info   
|__ `M_searchGYM.java` : Search GYM (with location, with gym name, recommend gym with member's location, enroll/change gym)  
|__ `M_searchTrainer.java` : Search trainer (with belonging gym name, with trainer name, show all trainer in member's gym, enroll/change trainer)    
|__ `M_enrollMembership.java` : Enroll/change membership     
|__ `M_totalLeft.java` : query to check the number of remaining classes (for changing gym or trainer)   
* **In addition,**  
  * Gym and trainer must be enrolled first before check the account info.
  * Gym, trainer, and membership can only be changed when the number of remaining classes is zero.
  * The order of enrollment is as follows : Gym -> Trainer -> Membership

### `Member_manageClass`
|__ `M_cancelClass.java` : Cancel a reserved class (Classes can be cancelled at least 5 hours before the start time).  
|__ `M_manageClass.java` : Menu page for class management of the current member.  
|__ `M_reserveClass.java` : Reserve a new class (Cannot reserve classes that are duplicated or against-time).  
|__ `M_seeFutureClasses.java` : View all the reserved classes of the current member.\
|__ `M_seepastClasses.java` : View all the expired classes of the current member.

### `Main`
|__ `Main.java` : Main() for total program  
|__ `StartScreen.java` : StartScreen to select "회원 member" "트레이너 trainer" "관장 gym_owner"  
|__ `LoginScreen.java` : Login screen for each user type\
|__ `JoinScreen.java` : Join screen for each user type\
|__ `DeleteScreen.java` : Membership withdraw screen for each user type

### Demo Video
https://drive.google.com/file/d/18B8mVigCWF0d21X77wYziEKeYyOiLZqo/view?usp=sharing

### Screenshots
![image](https://user-images.githubusercontent.com/90602936/177673705-58f6ccb7-476a-4c0a-a1a1-876d91775ab4.png)
![image](https://user-images.githubusercontent.com/90602936/177673729-fce39976-7fa1-4e33-885d-b8a4be41d599.png)
![image](https://user-images.githubusercontent.com/90602936/177673746-26805697-ccd3-48bb-9cd3-3fa6fb52868a.png)
![image](https://user-images.githubusercontent.com/90602936/177673769-78e00ee6-af84-43e1-93a9-d12cc82da8ac.png)
![image](https://user-images.githubusercontent.com/90602936/177673796-380c68d7-d395-43b0-8873-b65c7cb5236a.png)
![image](https://user-images.githubusercontent.com/90602936/177673817-92aff119-d261-43eb-9dea-1622c537b56b.png)
![image](https://user-images.githubusercontent.com/90602936/177673842-4daa2605-7a3e-492a-b6a6-7a164c2c5f0c.png)
![image](https://user-images.githubusercontent.com/90602936/177673874-0c0fa8f9-71ec-40ef-90fe-13fb4ba48f93.png)
