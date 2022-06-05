# DBProject2022 ( PT Reservation System )

### `Gym` (관장으로 로그인했을 때)
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
|__ `M_cancelClass.java` :      
|__ `M_manageClass.java` :   
|__ `M_reserveClass.java` :     
|__ `M_seeFutureClasses.java` :   
|__ `M_seepastClasses.java` :    
