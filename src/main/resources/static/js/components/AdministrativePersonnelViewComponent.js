// src/main/resources/static/js/components/AdministrativePersonnelViewComponent.js
Vue.component('administrative-personnel-view', {
    template: `
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div style="margin-left:200px;" v-if="administrativePersonnel">
                        <h2>{{ administrativePersonnel.jobTitle }}</h2>
                        <h3>Job Code: {{ administrativePersonnel.jobCode }}</h3>
                        <h3>Reference Code: {{ administrativePersonnel.referenceCode }}</h3>
                        <h3>Division/Unit: {{ administrativePersonnel.divisionUnit }}</h3>
                        <h3>Classification: {{ administrativePersonnel.classification }}</h3>
                        <h3>Terms of Employment: {{ administrativePersonnel.termsOfEmployment }}</h3>
                        <h3>FLSA Status: {{ administrativePersonnel.flsaStatus }}</h3>
                        <h3>Position Summary:</h3>
                        <p>{{ administrativePersonnel.positionSummary }}</p>
                        <h3>Essential Duties and Responsibilities:</h3>
                        <ul>
                            <li v-for="duty in administrativePersonnel.essentialDutiesAndResponsibilities.split(',')" :key="duty">{{ duty }}</li>
                        </ul>
                        <h3>Position Expectations:</h3>
                        <ul>
                            <li v-for="expectation in administrativePersonnel.positionExpectations.split(',')" :key="expectation">{{ expectation }}</li>
                        </ul>
                        <h3>Position Requirements:</h3>
                        <ul>
                            <li v-for="requirement in administrativePersonnel.positionRequirements.split(',')" :key="requirement">{{ requirement }}</li>
                        </ul>
                    </div>
                    <div v-else>
                        <p>Loading...</p>
                    </div>
                </div>
            </div>
        </div>
    `,
    data() {
        return {
            administrativePersonnel: null
        };
    },
    created() {
        const id = window.location.pathname.split('/').pop();
        axios.get(`/api/administrative-personnel/${id}`, {
            auth: {
                username: 'user',
                password: 'password'
            }
        })
            .then(response => {
                this.administrativePersonnel = response.data;
            })
            .catch(error => {
                console.error('There was an error fetching the data:', error);
            });
    }
});