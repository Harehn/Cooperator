<template>
  <div id="app" class="window">

    <!-- Header -->
    <div class="edge_top base section_horizontal" >
      <div class="edge_left">
        <img src="./assets/logo_cooperator.png" id="header_logo"/>
        <p style="color:#ffffff"> EMPLOYER SYSTEM </p>
      </div>
      <div class="edge_right vertically_aligned" v-if="view!=0">
        <ul>
          <button style="background-color: #b1c9ef">{{employer.company}}</button>
        <!-- <p style="display: inline-block;background-color :#b1c9ef; padding:3px">{{employer.company}}</p> -->
        <button v-on:click="changeView(1)">Home</button>
        <button v-on:click="changeView(2)">Employees</button>
        <button v-on:click="changeView(3)">Forms</button>
        <button v-on:click="changeView(4)">Events</button>
        </ul>
      </div>
    </div>

    <!-- Main -->
    <div class="main">

      <!-- Login View -->
      <LoginFrame v-if="view==0"/>

      <!-- Home View -->
      <Home  v-if="view==1"/>

      <!-- Employees View -->
      <!-- Creates Prop for EmployeeProfileVue -->
      <EmployeeProfileFrame v-bind:propEmployer="employer" v-if="view==2"/>

      <!-- Forms View -->
      <FormsFrame v-if="view==3"/>

      <!-- Social Events View -->
      <EmployerEventsFrame v-if="view==4"/>

      <!-- Password Changing View -->
      <PasswordChangeFrame v-if="view==6"/>

    </div>

    <!-- Footer -->
    <div class="edge_left edge_right paddedThin edge_to_bottom section_horizontal bgColor2">
      <div style="text-align:center;color:white;font-size:8px">
        <p>ECSE321 Winter 2019 Team 9 Project</p>
      </div>
      <div style="text-align:center">
        <img src="./assets/logo_mcgill.png" style="width:64px;height:81px" />
      </div>
    </div>

  </div>
</template>

<!------------------------------------------------------------------------------------------------------------------>

<script>

  import {APIService} from './components/APIService'; //Communication to backend handler
  const apiService = new APIService();


import EmployeeProfileVue from './components/EmployeeProfileVue.vue';
import LoginVue from './components/LoginVue.vue';
import Forms from './components/Forms.vue';
import Home from './components/Home.vue';
import EmployerEventsVue from './components/EmployerEvents.vue';
import PasswordChangeVue from './components/PasswordChangeVue.vue'

  import {EventBus} from './components/EventBus'; //Event handler: this vue sends messages.

  export default {
  name: 'app',
  components: {
    'EmployeeProfileFrame':EmployeeProfileVue,
    'LoginFrame':LoginVue,
    'FormsFrame':Forms,
    'EmployerEventsFrame': EmployerEventsVue,
    'Home': Home,
    'PasswordChangeFrame': PasswordChangeVue

  },
  data(){
    return {
      //Web-specific stuff
      view: 0,
      arr: ["Login","Home","Student","Download","Events","Alerts","ChangeLogIn"],
      //Data from the backend
      employer:{
        company:"",
        firstName:"",
        lastName:"",
      }
    }
  },

  methods:{
    //View changer method called by the header buttons
    changeView: function(newView){
      //View changing
      if(this.view==0){   //Prevent view changing if at login screen
        if(newView==0){
          this.view=1;
        }
      }
      else{
        this.view=newView;  //Change what view will be rendered
        if(newView==2){     //If going to Employees page, collect employee profiles
          console.log("Emiting openingEmployeePage event.");
          EventBus.$emit("openingEmployeePage", this.employer);
        }
        else if(newView == 1){
          EventBus.$emit("openingHomePage", this.employer);
        }
        else if(newView == 4){
          EventBus.$emit("openingInvitationsPage", this.employer);
        }
      }
    }
  },

  mounted(){
    EventBus.$on('succesfullyLoggedIn', employer=>{
      console.log("Deteceted succesful login in App.vue")
      this.employer.company = employer.company;
      this.employer.firstName = employer.firstName;
      this.employer.lastName = employer.lastName;
      this.changeView(0);
      console.log("View is now " + this.view);
    });
    EventBus.$on('openingPasswordChangePage', employer=>{
      this.changeView(6);
    });
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
button{
  background-color: #6e909d;
  color: white;
  border:none;
  border-radius: 4px;
  padding: 6px;
}
button:hover{
  background-color: #4286f4;
}
h1{
  font-family: "Trebuchet MS", sans-serif;
}

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
  /*position:absolute;
  margin-top: 100vh;*/
  position:relative;
  bottom:auto;
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
/* Unique */
.window{
}
.main{
  margin-top:150px;
}
.small p, pre{
  display: inline-block;
  text-align: center;
}
.centered{
  width:300px;
  margin:auto;
}
.linklike{
  text-align:center;
  text-decoration: underline;
  color:blue;
    cursor: pointer;
}
.side{
  width: 45%;
  float:left;
  margin:5px;
}
.side input,p{
  display: inline-block;
}
.clearing{
  clear:both;
}
#header_logo{
  width: 120px;
  height: 120px;
}
#table {
  width: 100%;
}

</style>
