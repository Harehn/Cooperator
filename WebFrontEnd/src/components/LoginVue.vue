<template>
  <div class="paddedThick small centered bgColor1">
    <div>
      <h1>Log in</h1>
    </div>
    <br/>
    <form>
      <div>
        <p>Username</p>
      </div>
      <div>
        <input type="username" v-model="username" placeholder="ex. empboi" autocomplete="on"></input>
      </div>
      <div v-if="validUsername==false" style="color:red">Username "{{prevUsername}}" not found; invalid.</div>
      <div v-if="username==''" style="color:maroon">Please enter your username.</div>
      <br/>
      <div>
        <p>Password</p>
      </div>
      <div>
        <input type="password" v-model="password" placeholder="ex. 1234" autocomplete="on"></input>
      </div>
      <div v-if="validUsername==true&&password==''&&validPassword==false&&fetching==false" style="color:red">
        Incorrect password.
      </div>
      <div v-if="validUsername==true&&password==''&&validPassword==true&&fetching==false" style="color:maroon">
        Please enter your password.
      </div>
      <div v-if="fetching==true" style="color:purple">
        Attempting to log in. Please wait...
      </div>
    </form>
    <br/>
    <div>
      <button v-on:click="attemptLogin()">Log in</button>
      <a href="">
        <span title="Feature not implemented. Sorry!">
          Forgot credentials
        </span>
      </a>
    </div>
  </div>
</template>

<!------------------------------------------------------------------------------------------------------------------>

<script>
import {APIService} from './APIService';  //Backend-communication handler
const apiService = new APIService();

import {EventBus} from './EventBus';      //Event handler

export default {
  name: 'LoginFrame',
  data(){
    return {
      username:'',        //Working username is 'empboi', but set to '' for release ofc
      prevUsername:'',
      validUsername:true,
      password:'',          //Working password is '1234', but set to '' for relase ofc
      validPassword:true,
      employer:null,
      fetching:false,
      fetchSuccess:false
    }
  },
  methods:{
    attemptLogin(){
      console.log("Entered attemptLogin(). Sanity checking...");
      if(this.username=='') return;
      if(this.password=='') return;
      var alphanumeric;
      alphanumeric = this.username.replace( /^[^a-z0-9]+$/i , "");
      if(alphanumeric.localeCompare(this.username)!=0) return;
      alphanumeric = this.password.replace( /^[^a-z0-9]+$/i , "");
      if(alphanumeric.localeCompare(this.password)!=0) return;
      console.log("Passed.\nAttmpting login!");
      //Ensures that the username exists
      this.prevUsername = this.username;
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
      //Consumes the password
      this.fetching = true;
      var consumedPassword = this.password;
      this.password = '';
      //Attempts to log in by obtaining the employer's information, necessary for using the rest of the vue's.
      apiService.login(this.username,consumedPassword).then( (data) => {
        this.employer = data;
        //Start of employer received-data error checking
        var employerIsProper = true;
        try{ this.employer.company; this.employer.lastName; this.employer.firstName; }
        catch(e){
          if(e.name == "ReferenceError"){
            employerIsProper = false;
            this.fetchSuccess = false;
            console.log(e);
          }
        }
        //End of employer received-data error checking
        this.fetching = false;
        this.fetchSuccess = true;
        if(employerIsProper){
          console.log("Received employer is proper!");
          EventBus.$emit('succesfullyLoggedIn',this.employer);
          console.log("Sounded the succesful login");
        }
      },
      (err) => {
        this.fetchSuccess = false;
        this.fetching = false;
        this.validPassword = false;
        console.log("Err\n",err);
      })
      .catch( (e) => {
        this.fetchSuccess = false;
        this.fetching = false;
        this.validPassword = false;
        console.log("Caught",e);
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
