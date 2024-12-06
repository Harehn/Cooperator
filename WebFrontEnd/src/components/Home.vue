<template>
  <div id="Home">
    <div class="edge_left edge_right bgColor1 paddedThick">
      <div class="edge_left bgColor3">
      <table class="miniTable">
          <thead>
            <tr>
              <td align="center" colspan="4" width="500px"><h1>Welcome {{employer.firstName}}</h1></td>
            </tr>
          </thead>
          <tbody>
             <tr>
             <th width="50%">Number of Students</th>
             <td width="50%">{{numberOfEmployeeProfiles}}</td>
             </tr>
             <tr>
             <th>Number of Evaluations to complete</th>
             <td>{{numberOfEvalsToComplete}}</td>
             </tr>
             <tr>
             <th>Number of Invitations</th>
             <td>{{numberOfinvitations}}</td>
             </tr>
          </tbody>
       </table>
       <button v-on:click="changePassword()">Change Password</button>
      </div>
    </div>
  </div>
</template>

<script>
import {APIService} from './APIService';  //Backend-communication handler
const apiService = new APIService();

import {EventBus} from './EventBus';      //Event handler

export default {
  name: 'Home',
  data(){
    return {
      //Data from the backend
      employer:{
        company:"",
        firstName:"",
        lastName:""
      },
      numberOfinvitations:0,
      numberOfEvalsToComplete:0,
      username:"",
      password:"",
      inputInvationId:0,
      numberOfEmployeeProfiles:0,
      queryState:'Current'
    }
  },
  methods:{
    setEmployer(employer){
      this.employer = employer;
    },
    update(){
      if( this.queryState == 'All' ){
        this.getAllEmployees();
      }
      if( this.queryState == 'Current' ){
        this.getCurrentEmployees();
      }
    },
    changePassword(){
      EventBus.$emit('openingPasswordChangePage');
    },
    //Data collector for all employees for an employer
    getInvitations(){
      apiService.getInvitations(this.employer.company,this.employer.lastName,this.employer.firstName)
      .then((data)=>{
        this.numberOfinvitations = data.length;
        console.log("getAllInvitations called.");
      });
    },
    //Data collector for all employees for an employer
    getAllEmployees(){
      apiService.getAllEmployees(this.employer.company,this.employer.lastName,this.employer.firstName)
      .then((data)=>{
        this.numberOfEmployeeProfiles = data.length;
        var end=this.numberOfEmployeeProfiles;
		    var i;
        for (i = 0; i < end ; i++) {
            if(data[i].evaluationOfStudent==null){
              this.numberOfEvalsToComplete++;
            }
        }
        console.log("getAllEmployees called.");
      });
    },
    //Data collector for all current employees for an employer
    getCurrentEmployees(){
      apiService.getCurrentEmployees(this.employer.company,this.employer.lastName,this.employer.firstName)
      .then((data)=>{
        this.numberOfEmployeeProfiles = data.length;
        var end= this.numberOfEmployeeProfiles;
        var i;
		    for (i = 0; i < end ; i++) {
            if(data[i].evaluationOfStudent==null){
              this.numberOfEvalsToComplete++;
            }
        }  
        console.log("getCurrentEmployees called.");
      });
    }

  },
  mounted(){
    EventBus.$on('openingHomePage', employer => {
      this.employer.company = employer.company;
      this.employer.firstName = employer.firstName;
      this.employer.lastName = employer.lastName;
      this.getInvitations();
      this.getCurrentEmployees();
      //this.update();
      console.log("Opening Home vue")
    });
  }

}
</script>


<style>

</style>
