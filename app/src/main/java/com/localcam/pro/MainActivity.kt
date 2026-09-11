package com.localcam.pro

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
 override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { LocalCamTheme { LocalCamApp() } } }
}

enum class Role { CAMERA, MONITOR }
class AppViewModel : androidx.lifecycle.ViewModel() {
 var role by mutableStateOf<Role?>(null); var paired by mutableStateOf(false); var streaming by mutableStateOf(false); var recording by mutableStateOf(false)
 fun choose(r: Role) { role = r }
}

@Composable fun LocalCamApp(vm: AppViewModel = viewModel()) {
 Surface(Modifier.fillMaxSize(), color = Color(0xFF090A12)) {
  when (vm.role) { null -> Welcome({ vm.choose(Role.CAMERA) }, { vm.choose(Role.MONITOR) }); Role.CAMERA -> CameraDashboard(vm); Role.MONITOR -> MonitorDashboard(vm) }
 }
}
@Composable fun Header(title: String, status: String) { Column(Modifier.padding(20.dp)) { Text("LocalCam Pro", color=Color(0xFFB79CFF), style=MaterialTheme.typography.labelLarge); Spacer(Modifier.height(8.dp)); Text(title, style=MaterialTheme.typography.headlineMedium, fontWeight=FontWeight.Bold); StatusChip(status) } }
@Composable fun StatusChip(text:String) { AssistChip(onClick={}, label={Text(text)}, leadingIcon={Text("●", color=Color(0xFF77E0A0))}) }
@Composable fun Welcome(camera:()->Unit, monitor:()->Unit) { Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement=Arrangement.Center) { Text("LocalCam Pro", style=MaterialTheme.typography.displaySmall, color=Color(0xFFB79CFF), fontWeight=FontWeight.Bold); Text("Private, local baby monitor", style=MaterialTheme.typography.titleMedium); Spacer(Modifier.height(18.dp)); Text("Choose how this phone will be used. Video and audio stay on your local network."); Spacer(Modifier.height(28.dp)); Button(camera, Modifier.fillMaxWidth()) { Text("Use as Camera Phone") }; Spacer(Modifier.height(12.dp)); OutlinedButton(monitor, Modifier.fillMaxWidth()) { Text("Use as Monitor Phone") }; Spacer(Modifier.height(24.dp)); Text("No cloud • No accounts • Visible recording indicators", style=MaterialTheme.typography.bodySmall) } }
@Composable fun PermissionGate(content:@Composable ()->Unit) { val launcher=rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){}; LaunchedEffect(Unit){launcher.launch(arrayOf(Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO))}; content() }
@Composable fun CameraDashboard(vm:AppViewModel) { PermissionGate { Column(Modifier.fillMaxSize()) { Header("Camera phone", if(vm.streaming) "Streaming" else "Ready"); Box(Modifier.fillMaxWidth().weight(1f), contentAlignment=Alignment.Center){ Text(if(vm.streaming) "● LIVE CAMERA PREVIEW\n\nMicrophone: ${if(vm.streaming) "ACTIVE" else "OFF"}" else "Camera preview will appear here", color=Color.LightGray) }; Row(Modifier.padding(16.dp), horizontalArrangement=Arrangement.spacedBy(10.dp)){ Button({vm.streaming=!vm.streaming}){Text(if(vm.streaming)"Stop streaming" else "Start streaming")}; OutlinedButton({vm.recording=!vm.recording}){Text(if(vm.recording)"Stop recording" else "Record")}}; Text("Pairing  •  ${if(vm.paired)"Paired" else "Not paired"}", Modifier.padding(20.dp)); OutlinedButton({vm.paired=true}, Modifier.padding(16.dp)){Text("Pair device")}} } }
@Composable fun MonitorDashboard(vm:AppViewModel) { Column(Modifier.fillMaxSize()) { Header("Monitor phone", if(vm.paired) "Paired" else "Disconnected"); Box(Modifier.fillMaxWidth().weight(1f), contentAlignment=Alignment.Center){Text(if(vm.paired)"Waiting for camera stream…" else "Pair with a camera phone", color=Color.LightGray)}; Button({vm.paired=true}, Modifier.fillMaxWidth().padding(16.dp)){Text("Pair on local network")}; Text("Audio muted  •  Push to talk available after connection", Modifier.padding(20.dp)) } }
@Composable fun LocalCamTheme(content:@Composable ()->Unit) { MaterialTheme(colorScheme=darkColorScheme(primary=Color(0xFFB79CFF), secondary=Color(0xFF79C7FF)), content=content) }
