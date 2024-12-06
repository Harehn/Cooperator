<template>
  <div class="paddedThick bgColor1">
    <div>
      <h2>Password Changing Form</h2>
    </div>
    <br/>

    <div v-if="announcement!=''" style="color:darkgreen">
      <div v-if="successFetch==true" style="color:darkgreen">
        {{announcement}}
      </div>
      <div v-if="successFetch==false" style="color:red">
        {{announcement}}
      </div>
    </div>

    <form>

      <div>
        <p>Username</p>
      </div>
      <div>
        <input type="username" v-model="username" placeholder="Username here" autocomplete="on"/>
      </div>
      <div v-if="fetching==false">
        <div v-if="msgUsername!=''" style="color:red">
          {{msgUsername}}
        </div>
        <div v-else>
          <div v-if="username==''" style="color:maroon">
            Please enter your username.
          </div>
          <div v-else>
            <div v-if="validUsername==false" style="color:red">
              Username "{{prevUsername}}" not found; invalid.
            </div>
          </div>
        </div>
      </div>

      <br/>

      <div>
        <p>Current Password</p>
      </div>
      <div>
        <input type="password" v-model="password" placeholder="Password here" autocomplete="off"/>
      </div>
      <div v-if="fetching==false">
        <div v-if="msgPassword!=''" style="color:red">
          {{msgPassword}}
        </div>
        <div v-else>
          <div v-if="password==''&&validUsername==true&&username!=''">
            <div v-if="validPassword==true" style="color:maroon">
              Please enter your current password.
            </div>
            <div v-else style="color:red">
              Incorrect password. You may try again.
            </div>
          </div>
        </div>
      </div>
      <br/>

      <div>
        <p>New Password</p>
      </div>
      <div>
        <input type="password" v-model="newPassword1" placeholder="New password here" autcomplete="off"/>
      </div>
      <div v-if="fetching==false">
        <div v-if="msgNewPassword1!=''" style="color:red">
          {{msgNewPassword1}}
        </div>
        <div v-else>
          <div v-if="newPassword1==''&&validUsername==true&&username!=''&&password!=''">
            <div v-if="validPassword==true" style="color:maroon">
              Please enter your current password.
            </div>
            <div v-else style="color:red">
              Incorrect password. You may try again.
            </div>
          </div>
        </div>
      </div>
      <br/>

      <div>
        <p>Confirm new Password</p>
      </div>
      <div>
        <input type="password" v-model="newPassword2" placeholder="Confirm new password" autcomplete="off"/>
      </div>
      <div v-if="fetching==false">
        <div v-if="msgNewPassword2!=''" style="color:red">
          {{msgNewPassword2}}
        </div>
        <div v-else>
          <div v-if="newPassword2==''&&validUsername==true&&username!=''&&password!=''&&newPassword1!=''">
            <div v-if="validPassword==true" style="color:maroon">
              Please enter your current password.
            </div>
            <div v-else style="color:red">
              Incorrect password. You may try again.
            </div>
          </div>
        </div>
      </div>

      <div v-if="fetching==true" style="color:purple">
        Attempting to change password. Please wait...
      </div>

    </form>
    <br/>
    <div>
      <button v-on:click="attemptChange()">Change Password</button>
    </div>
    <div v-if="announcement!=''" style="color:darkgreen">
      <div v-if="successFetch==true" style="color:darkgreen">
        {{announcement}}
      </div>
      <div v-if="successFetch==false" style="color:red">
        {{announcement}}
      </div>
    </div>
  </div>
</template>

<!------------------------------------------------------------------------------------------------------------------>

<script>
import {APIService} from './APIService';  //Backend-communication handler
const apiService = new APIService();

import {EventBus} from './EventBus';      //Event handler

export default {
  name: 'PasswordChangeFrame',
  data(){
    return {
      username:'',
      validUsername:true,
      msgUsername:'',
      password:'',
      validPassword:true,
      msgPassword:'',
      newPassword1:'',
      validNewPassword1:true,
      msgNewPassword1:'',
      newPassword2:'',
      validNewPassword2:true,
      msgNewPassword2:'',
      fetching:false,
      announcement:'',
      successFetch:true
    }
  },
  methods:{
    attemptChange(){
      //Remove any announcements
      this.announcement = '';
      //Safety checks
      console.log("Entered attemptLogin(). Sanity checking...");
      //Safety check attribute
      var problemDetected = false;
      var alphanumeric; //temp storage for regular expression checks
      //Actual safety checks
      alphanumeric = this.username.replace( /^[^a-z0-9]+$/i , "");
      if(this.username==''){
        problemDetected = true;
      } else if(alphanumeric.localeCompare(this.username)!=0){
        problemDetected = true;
        this.msgUsername="Detected the illegal use of non-alphanumerics in username.";
      } else{
        this.msgUsername='';
      }
      alphanumeric = this.password.replace( /^[^a-z0-9]+$/i , "");
      if(this.password==''){
        problemDetected = true;
      } else if(alphanumeric.localeCompare(this.password)!=0){
        problemDetected = true;
        this.msgPassword="Detected the illegal use of non-alphanumerics in current password.";
      } else{
        this.msgPassword='';
      }
      alphanumeric = this.newPassword1.replace( /^[^a-z0-9]+$/i , "");
      if(this.newPassword1==''){
        problemDetected = true;
      } else if(alphanumeric.localeCompare(this.newPassword1)!=0) {
        problemDetected = true;
        this.msgNewPassword1="Detected the illegal use of non-alphanumerics in new password.";
      } else{
        this.msgNewPassword1='';
      }
      alphanumeric = this.newPassword2.replace( /^[^a-z0-9]+$/i , "");
      if(this.newPassword2==''){
        problemDetected = true;
      } else if(alphanumeric.localeCompare(this.newPassword2)!=0){
        problemDetected = true;
        this.msgNewPassword2="Detected the illegal use of non-alphanumerics in new password confirmation input.";
      } else if(this.newPassword1.localeCompare(this.newPassword2)!=0){
        problemDetected = true;
        this.msgNewPassword2="The new passwords do not match.";
      } else{
        this.msgNewPassword2='';
      }
      //Check if there were any safety issues. If there were, stop here.
      if(problemDetected){
        console.log("Problem detected in changing-password-form's input.!");
        return;
      }
      //Confirm the passing of static safety tests
      console.log("Passed.\nAttmpting login!");

      //Ensures that the username exists
      apiService.checkUsername(this.username).then( (data) => {
        this.validUsername = data;
        console.log("username\'s validity is " + data);
      },
      (err) => {
        console.log("Err\n",err);
        this.validUserName = false;
      })
      .catch( (e) => {
        console.log("Caught",e);
        this.validUserName = false;
      });
      //Consumes the passwords
      this.fetching = true;
      var consumedPassword = this.password;
      var consumedNewPassword1 = this.newPassword1;
      var consumedNewPassword2 = this.newPassword2;
      this.password = '';
      this.newPassword1 = '';
      this.newPassword2 = '';
      //Attempts to log in by obtaining the employer's information, necessary for using the rest of the vue's.
      apiService.changePassword(this.username,consumedPassword,consumedNewPassword2,consumedNewPassword2)
        .then( (data) =>
          {
            console.log("Success!");
            this.fetching = false;
            this.announcement = "Succesfully changed password!";
          },
          (err) =>
          {
            console.log("Err\n",err);
            this.fetching = false;
            this.announcement = "Password changing errored and abandoned; old password kept. Please try again later.";
          }
        )
        .catch( (e) => {
          console.log("Caught",e);
          this.fetching = false;
          this.announcement = "Password changing errored and abandoned; old password kept. Please try again later.";
        });
    }
  }

}
</script>

<!------------------------------------------------------------------------------------------------------------------>

<style>
/* Global */
{
    background-color:blue;
}
/* Multi-use */
.base{
  position:relative;
}
.edge_top{
  position:relative;
  top:5px;
  padding-top:25px;
}
.edge_sides{
  position:absolute;
  left:5px;
  right:5px;
  padding-left:25px;
  padding-right:25px;
}
.edge_left{
  position:absolute;
  left:5px;
  padding-left:25px;
}
.edge_right{
  position:absolute;
  right:5px;
  padding-right:25px;
}
.edge_bottom{
  position:absolute;
  bottom:5px;
  padding-bottom:25px;
}
.edge_to_bottom{
  position:absolute;
  margin-top: 100vh;
}
.section_horizontal{
  width:100%;
}
.vertically_aligned{
  align-items:centered;
  text-align:center;
}
.bgColor1{
  background-color:#b1edff;
}
.bgColor2{
  background-color:#2b323c;
}
.bgColor3{
  background-color:#6e909d;
}
.paddedThick{
  padding:50px;
}
.paddedThin{
padding:10px;
}
.miniTable table,th,td{
  border: 1px solid black
}

</style>
