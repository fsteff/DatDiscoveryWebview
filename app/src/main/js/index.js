var DSS = require('./discovery-swarm-stream/client')

var hyperdrive = require('hyperdrive')
var RAM = require('random-access-memory')
var websocket = require('websocket-stream')

// Beaker browser website key
var archiveKey = '1f21654ca808a09867e0ce65c0e2d493002ad55e3eb585a64ed35f9f4f60ac23'

var archive = hyperdrive(() => new RAM(), archiveKey)

var ws = new WebSocket('ws://localhost:3495')
ws.binaryType = 'arraybuffer'
var socket = websocket(ws)

socket.on('end', () => {
  console.log('websocket end')
})

function replicate (info) {
  // In a multi-archive scenario you'd want to look up which archive should be used via info.channel
  return archive.replicate({
    sparse: true,
    live: true
  })
}

var swarm = new DSS({
  connection: socket,
  // Override this so that it does handshaking through the replication stream
  stream: replicate
})

archive.on('ready', () => {
  swarm.join(archive.discoveryKey.toString('hex'), null, () => {
    console.log('open')
    archive.readFile('demo.txt', null, (err, data) => {
      if (err) console.error(err)

      document.getElementById('loading').remove()

      const elem = document.createElement('div')
      elem.innerHTML = data.toString()
      document.body.appendChild(elem)
    })
  })
})
