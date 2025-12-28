package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.* // collectAsState, getValue, setValue buradan gelir
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yeditepe.seventalkapp.viewmodel.ClubsViewModel

@Composable
fun RegisterScreen(
    onLoginClick: () -> Unit,
    onRegisterSuccess: (String) -> Unit,
    viewModel: ClubsViewModel = viewModel()
) {
    // ARTIK HATA VERMEYECEK: ViewModel içindeki faculties verisini dinliyoruz
    val facultyList by viewModel.faculties.collectAsState()

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }

    var selectedFaculty by remember { mutableStateOf("") }
    var selectedDepartment by remember { mutableStateOf("") }
    var selectedGrade by remember { mutableStateOf("") }

    var showAvatarDialog by remember { mutableStateOf(false) }
    var selectedAvatar by remember { mutableStateOf(Icons.Default.Add) }

    // Seçilen fakülteye göre bölümleri filtrele
    val availableDepartments = remember(selectedFaculty, facultyList) {
        if (selectedFaculty == "Diğer") {
            listOf("Diğer")
        } else {
            facultyList.find { it.name == selectedFaculty }?.departments ?: emptyList()
        }
    }

    val gradeList = listOf("Hazırlık", "1.Sınıf", "2.Sınıf", "3.Sınıf", "4.Sınıf", "5.Sınıf", "6.Sınıf", "Mezun")

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFE3F2FD))) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {

                    // Avatar Butonu
                    Box(
                        modifier = Modifier.size(80.dp).clip(CircleShape).background(Color(0xFFE0E0E0)).clickable { showAvatarDialog = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = selectedAvatar, contentDescription = null, modifier = Modifier.size(40.dp), tint = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Kişisel Bilgiler", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text("Lütfen bilgilerinizi doğru şekilde doldurunuz.", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 24.dp))

                    // AD - SOYAD
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        CustomTextField(value = name, onValueChange = { name = it }, label = "Adınız", modifier = Modifier.weight(1f))
                        CustomTextField(value = surname, onValueChange = { surname = it }, label = "Soyadınız", modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // FAKÜLTE SEÇİMİ
                    // Sadece isimleri alıp listeye veriyoruz
                    val facultyNames = facultyList.map { it.name }
                    DropdownTextField(
                        label = "Fakülteniz",
                        options = facultyNames,
                        selectedOption = selectedFaculty,
                        onOptionSelected = { newFaculty ->
                            selectedFaculty = newFaculty
                            selectedDepartment = "" // Fakülte değişince bölüm sıfırlansın
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // BÖLÜM VE SINIF
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        DropdownTextField(
                            label = "Bölümünüz",
                            options = availableDepartments,
                            selectedOption = selectedDepartment,
                            onOptionSelected = { selectedDepartment = it },
                            modifier = Modifier.weight(2f),
                            enabled = selectedFaculty.isNotEmpty()
                        )
                        DropdownTextField(
                            label = "Sınıf",
                            options = gradeList,
                            selectedOption = selectedGrade,
                            onOptionSelected = { selectedGrade = it },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            val fullName = "$name $surname"
                            if (fullName.isNotBlank()) onRegisterSuccess(fullName) else onRegisterSuccess("Yeni Kullanıcı")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Text("İlerle", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Geri dön", color = Color(0xFF90CAF9), modifier = Modifier.clickable { onLoginClick() })
                }
            }
        }

        if (showAvatarDialog) {
            AvatarSelectionDialog(onDismiss = { showAvatarDialog = false }, onAvatarSelected = { selectedAvatar = it; showAvatarDialog = false })
        }
    }
}

// --- YARDIMCI BİLEŞENLER ---

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownTextField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { if (enabled) expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label, fontSize = 12.sp) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color(0xFFF5F5F5),
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Color(0xFF4CAF50)
            ),
            enabled = enabled
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            if (options.isEmpty()) {
                DropdownMenuItem(text = { Text("Seçenek yok") }, onClick = { expanded = false })
            } else {
                options.forEach { option ->
                    DropdownMenuItem(text = { Text(option) }, onClick = { onOptionSelected(option); expanded = false })
                }
            }
        }
    }
}

@Composable
fun CustomTextField(value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 12.sp) },
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color(0xFF4CAF50)
        ),
        singleLine = true
    )
}

@Composable
fun AvatarSelectionDialog(onDismiss: () -> Unit, onAvatarSelected: (ImageVector) -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Bir avatar seçin", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
                val avatars = listOf(Icons.Default.Face, Icons.Default.Star, Icons.Default.Favorite, Icons.Default.Person, Icons.Default.ThumbUp, Icons.Default.Build, Icons.Default.Call, Icons.Default.Email, Icons.Default.Home)
                Column {
                    avatars.chunked(3).forEach { rowAvatars ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            rowAvatars.forEach { icon ->
                                IconButton(onClick = { onAvatarSelected(icon) }, modifier = Modifier.size(60.dp).padding(4.dp).background(Color(0xFFFFE0B2), CircleShape)) {
                                    Icon(icon, contentDescription = null, tint = Color.Black)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onDismiss, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)), shape = RoundedCornerShape(20.dp)) { Text("Kapat") }
            }
        }
    }
}