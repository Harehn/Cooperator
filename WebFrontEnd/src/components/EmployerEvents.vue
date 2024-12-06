<template>
        <div  id="employeeprofilemenu">
    <div class="edge_left edge_right bgColor4 paddedThick">
      <div id="table" class="edge_left bgColor3">
        <h1>Invitations </h1>
        <table id="table" class="miniTable">
          <thead>
            <tr>
            <tr>
            <tr>
              <th>Event Name</th>
              <th>Id</th>
              <th>Event Description</th>
              <th>Location</th>
              <th>Start Date</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="invitation in invitations" >
              <th>{{invitation.socialEventName}}</th>
              <th>{{invitation.id}}</th>
              <th>{{invitation.socialEventDescription}}</th>
              <th>{{invitation.socialEventLocation}}</th>
              <th>{{invitation.start}}</th>
              <th>{{invitation.status}}</th>
            </tr>
        </tbody>
          <tfoot>
            <td colspan="4" style="font-size:8px;color:lightgrey;padding:2px">
            </td>
            <td colspan="4" style="font-size:8px;color:lightgrey;padding:2px">
              
            </td>
          </tfoot>
        </table>
        <div>
          <br>
          <br>
          <br>
        <form>
          <input v-model="inputInvationId" placeholder="Enter Event Id"> </input>
          <input type="username" v-model="username" placeholder="username here" autocomplete="on"></input>
          <input type="password" v-model="password" placeholder="password here" autocomplete="on"></input>
          <br>
          <button v-on:click="acceptInvitation()"> Accept</button> 
          <button v-on:click="rejectInvitation()"> Reject</button> 
        </form>
        </div>
      </div>
    </div>
      
    </div>
</template>

<script>
import {APIService} from './APIService';  //Backend-communication handler
const apiService = new APIService();

import {EventBus} from './EventBus';      //Event handler

export default {
  name: 'EmployerEventsFrame',
  data(){
    return {
      //Data from the backend
      employer:{
        company:'',
        firstName:'',
        lastName:''
      },
      invitation:{
        socialEventName:"",
        id:"",
        socialEventDescription:"",
        socialEventLocation:"",
        start:[, , ,],
        status:""
       },
      invitations: [],
      invitation:null,
      numberOfinvitations:0,
      username:"",
      password:"",
      inputInvationId:0

    }
  },
  methods:{
    //Data collector for all employees for an employer
    getInvitations(){
      apiService.getInvitations(this.employer.company,this.employer.lastName,this.employer.firstName)
      .then((data)=>{
        this.invitations = data;
        this.numberOfinvitations = data.length;
        console.log("getAllInvitations called.");
      });
    },
    acceptInvitation(){
      apiService.acceptInvitation(this.inputInvationId,this.username,this.password)
      .then((data)=>{
        this.getInvitations();
        this.inputInvationId = "";
      });
    },
    rejectInvitation(){
      apiService.refuseInvitation(this.inputInvationId,this.username,this.password)
      .then((data)=>{
        this.getInvitations();
        this.inputInvationId = "";
      });
    }

},
  mounted(){
    EventBus.$on('openingInvitationsPage', employer => {
      this.employer.company = employer.company;
      this.employer.firstName = employer.firstName;
      this.employer.lastName = employer.lastName;
      console.log("Received openingInvitationsPage.")
      this.getInvitations();
    });
  }
}
</script>

<style>
</style>