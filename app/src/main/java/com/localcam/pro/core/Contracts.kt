package com.localcam.pro.core

import kotlinx.coroutines.flow.Flow

data class CameraStatus(val streaming:Boolean=false, val recording:Boolean=false, val torch:Boolean=false, val zoom:Float=1f)
data class PairingInfo(val deviceId:String, val name:String, val role:String, val fingerprint:String)
interface CameraRepository { val status: Flow<CameraStatus>; suspend fun startPreview(); suspend fun stopPreview(); suspend fun setTorch(enabled:Boolean); suspend fun setZoom(value:Float); suspend fun capturePhoto(); suspend fun startRecording(); suspend fun stopRecording() }
interface WebRtcRepository { val connectionState: Flow<String>; suspend fun createOffer():String; suspend fun acceptAnswer(sdp:String); suspend fun addIceCandidate(candidate:String); fun close() }
interface PairingRepository { val pairedDevices: Flow<List<PairingInfo>>; suspend fun pair(info:PairingInfo, pin:String):Boolean; suspend fun unpairAll() }
interface SignalingRepository { suspend fun startHost(port:Int):String; suspend fun connect(host:String,port:Int); suspend fun send(message:String); val messages:Flow<String>; fun close() }
interface RecordingRepository { val recordings:Flow<List<Recording>>; suspend fun delete(id:String) }
data class Recording(val id:String,val createdAt:Long,val durationMs:Long,val bytes:Long)
interface AlertRepository { val alerts:Flow<List<Alert>>; suspend fun add(alert:Alert) }
data class Alert(val timestamp:Long,val type:String,val detail:String)
