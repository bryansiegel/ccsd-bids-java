// src/main/resources/static/js/components/AdministrativePersonnelComponent.js
Vue.component('administrative-personnel', {
    template: `
        <div>
            <h1>Administrative Personnel</h1>
            <table>
                <thead>
                <tr>
                    <th>Job Title</th>
                    <th>Job Code</th>
                    <th>Reference Code</th>
                    <th>Division/Unit</th>
                    <th>Classification</th>
                    <th>Terms of Employment</th>
                    <th>FLSA Status</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="admin in administrativePersonnel" :key="admin.id">
                    <td>{{ admin.jobTitle }}</td>
                    <td>{{ admin.jobCode }}</td>
                    <td>{{ admin.referenceCode }}</td>
                    <td>{{ admin.divisionUnit }}</td>
                    <td>{{ admin.classification }}</td>
                    <td>{{ admin.termsOfEmployment }}</td>
                    <td>{{ admin.flsaStatus }}</td>
                    <td><a :href="'/administrative-personnel/' + admin.id">View</a></td>
                </tr>
                </tbody>
            </table>
        </div>
    `,
    data() {
        return {
            administrativePersonnel: []
        };
    },
    created() {
        axios.get('/api/administrative-personnel', {
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