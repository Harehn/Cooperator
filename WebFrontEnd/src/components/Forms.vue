<template>
    <div id="formsmenu">
        <div class="edge_left edge_right bgColor1 paddedThick">
            <table>
                <thead>
                    <tr>
                        <td align="center" colspan="4" class="bgColor2">
                            <p class= serif>Tax Forms</p>
                            </td>
                    </tr>
                    <tr>
                        <th>Form Name</th>
                        <th>Year</th>
                        <th>Description</th>
                        <th>Download Link</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="tForms in taxForms" >
                        <th>{{tForms.name}}</th>
                        <th>{{tForms.year}}</th>
                        <th>{{tForms.description}}</th>
                        <th>
                            <button class="button button1" v-on:click="openTab(completeURL(`${tForms.uri}/${tForms.name}`))" >
                                <b>Download Tax Form</b>
                            </button>
                        </th>
                    </tr>
                </tbody>
                <tfoot>
                    <td colspan="4" style="font-size:8px;color:dimgrey;padding:2px">
                    Click on buttons to download files.
                    </td>
                </tfoot>
            </table>
            <br/>
            <table>
                <thead>
                    <tr>
                        <td align="center" colspan="2" class="bgColor2">
                            <p class= serif>Student Evaluation Form</p>
                        </td>
                    </tr>
                    <tr>
                        <th>Instruction</th>
                        <th>Download Link</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-if="studentEvaluationForm">
                        <th>
                            {{studentEvaluationForm.instructions}}
                        </th>
                        <th>
                            <button class="button button1" v-on:click="openTab(completeURL(studentEvaluationForm.uri))">
                                <b>Download Evaluation Form</b>
                            </button>
                        </th>
                    </tr>
                </tbody>
                <tfoot>
                    <td colspan="2" style="font-size:8px;color:dimgrey;padding:2px">
                        Click on buttons to download files.
                    </td>
                </tfoot>
            </table>
        </div>
    </div>
</template>



<script>
import {APIService} from './APIService';  //Backend-communication handler
const apiService = new APIService();
import {EventBus} from './EventBus';      //Event handler

export default {

    data() {
        //Data from the backend
        return{
            taxForms:[],
            numberOfTaxForms:0,
            studentEvaluationForm:null,
        }
    },

    methods:{
    setTaxForm(taxForm){
      this.taxForm = taxForm;
    },

    setEvaForm(studentEvaluationForm){
      this.studentEvaluationForm = studentEvaluationForm;
    },

    //Data collector for descriptions of all tax forms
    getTaxFormDescriptorsOfAll(){
      apiService.getTaxFormDescriptorsOfAll()
      .then((data)=>{
        this.taxForms = data;
        this.numberOfTaxForms = data.length;
        console.log("getTaxFormDescriptorsOfAll called.");
      });
    },

    //Show the empty student evaluation form.
    getBlankStudentEvaluationFormDescriptors(){
      apiService.getBlankStudentEvaluationFormDescriptors()
      .then((data)=>{
          console.log(data);
          this.studentEvaluationForm = data;
        console.log("getBlankStudentEvaluationFormDescriptors called.");
      });
    },

    //Returns complete http request for downloadig a doc
    completeURL(uri){
        var API_URL = 'https://cooperator-backend-9.herokuapp.com';
        if( process.env.NODE_ENV == 'development' ){
            API_URL = 'http://localhost:8000';
            console.log("changed API_URL to localhost for development!");
        }
        return `${API_URL}${uri}`
    },

    //Opens new tab to provided url
    openTab(URL){
        console.log(URL);
        window.open(URL,"_Blank");
    }

  },

  mounted(){
      console.log("Mounted is called in Forms.vue.");
      this.getTaxFormDescriptorsOfAll();
      this.getBlankStudentEvaluationFormDescriptors();
  }

}
</script>



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
  margin-top: 150vh;
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
p.serif {
  font-family: "Times New Roman", Times, serif;
    font-size: 2.5em;
    color: #ffffff;
}
table {
  border-collapse: collapse;
  width: 100%;
}


th, td {
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {background-color: #f2f2f2;}

.button {
  background-color: #d3d3d3; 
  border: none;
  color: black;
  padding: 20px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  cursor: pointer;
}
.button1 {
    border-radius: 12px;
    border: 2px solid #ffffff;
}

</style>
