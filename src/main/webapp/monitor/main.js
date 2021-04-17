// REQUIRE NPM PACKAGES
const http = require('http');
const express = require('express');
const app = express();
const httpServer = http.createServer(app);
const osUtils = require('node-os-utils');
const os = require('os');
const axios = require('axios').default;
// const dk = require('diskusage-ng');
const nodeDiskInfo = require('node-disk-info');
const io = require('socket.io')(httpServer);
// View Engine ans static public folder
app.set('view engine', 'ejs');
app.use(express.static(__dirname + '/public'));
// Route
app.get('/', (req, res) => {
    res.render('index.ejs');
});
// CPU USAGE
const cpu = osUtils.cpu;
// USER and OS
const username = os.userInfo([{ encoding: 'buffer' }]).username;
const osInfo = os.type();
// SOCKET IO
io.on('connection', socket => {
    console.log(`${socket.id} connected`);
    // USE SET INTERVAL TO CHECK RAM USAGE EVERY SECOND
    setInterval(() => {
        // free -m in terminal: to get RAM's informations
        let swap = 2047 * 1024 * 1024;
        let ramUsed = Math.round(os.totalmem()) - Math.round(os.freemem()) - swap;
        // RAM percentage
        let ram = ((ramUsed * 100 / Math.round(os.totalmem()))).toFixed(0);
        // console.log(ram + '%')
        // dk('/root', function(err, usage) {
        //     if (err) return console.log(err);
        //     let tmp = usage.total/1024/1024/1024;
        //     return tmp;
        // });
        // // var useddk = dk('/root', function(err, usage) {
        // //     if (err) return console.log(err);  
        // //     let tmp =usage.used/1024/1024/1024;
        // //     return tmp;
        // // });
        // console.log('Total: ' + dk());
        // console.log('Used: ' + useddk);
        try {
            disks = nodeDiskInfo.getDiskInfoSync();
            let diskUsage = (getDiskUsage(disks) / 1024 / 1024).toFixed(0);
            let diskTotal = (getDiskTotal(disks) / 1024 / 1024).toFixed(0);

            let networkReceiveEns33;
            let networkTransmitEns33;
            let networkTransmitEns34;
            let networkReceiveEns34;
            axios({
                    method: 'get',
                    url: 'http://localhost:5808/api/network/transmit/ens33',
                    responseType: 'text'
                })
                .then(function(response) {
                    networkTransmitEns33 = response.data;
                });
            axios({
                    method: 'get',
                    url: 'http://localhost:5808/api/network/receive/ens33',
                    responseType: 'text'
                })
                .then(function(response) {
                    networkReceiveEns33 = response.data;
                });
            axios({
                    method: 'get',
                    url: 'http://localhost:5808/api/network/transmit/ens34',
                    responseType: 'text'
                })
                .then(function(response) {
                    networkTransmitEns34 = response.data;
                });
            axios({
                    method: 'get',
                    url: 'http://localhost:5808/api/network/receive/ens34',
                    responseType: 'text'
                })
                .then(function(response) {
                    networkReceiveEns34 = response.data;
                });

            // CPU USAGE PERCENTAGE
            cpu.usage().then(cpu => socket.emit('ram-usage', { ram, cpu, diskUsage, diskTotal, networkTransmitEns33, networkReceiveEns33, networkTransmitEns34, networkReceiveEns34 }));
        } catch (e) {
            console.error(e);
        }

        function getDiskUsage(disks) {
            let use = 0;
            for (const disk of disks) {
                if (disk.filesystem.includes('sda')) {
                    //df -h
                    use += (disk.blocks - disk.available);
                }
            }
            return use;
        }

        function getDiskTotal(disks) {
            let total = 0;
            for (const disk of disks) {
                if (disk.filesystem.includes('sda')) {
                    //df -h
                    total += (disk.blocks);
                }
            }
            return total;
        }
    }, 1000);
});

// Run the server
let PORT = 3001;
httpServer.listen(PORT, () => {
    console.log(`Server running on port: ${PORT}`)
});