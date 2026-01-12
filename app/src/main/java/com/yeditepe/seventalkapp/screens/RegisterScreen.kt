package com.yeditepe.seventalkapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yeditepe.seventalkapp.R
import com.yeditepe.seventalkapp.viewmodel.ClubsViewModel

@Composable
fun RegisterScreen(
    onLoginClick: () -> Unit,
    onRegisterSuccess: (String, Int) -> Unit,
    viewModel: ClubsViewModel = viewModel()
) {
    val facultyList by viewModel.faculties.collectAsState()

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var selectedFaculty by remember { mutableStateOf("") }
    var selectedDepartment by remember { mutableStateOf("") }
    var selectedGrade by remember { mutableStateOf("") }
    var showAvatarDialog by remember { mutableStateOf(false) }
    var selectedAvatarResId by remember { mutableStateOf(R.drawable.ppgroup1) }

    // HATA MESAJI KONTROLÜ İÇİN STATE
    var showError by remember { mutableStateOf(false) }

    // FİGMA RENKLERİ
    val figmaGreen = Color(0xFF40B353)
    val figmaBackgroundBase = Color(0xFF4F24A6)
    val figmaRedWarning = Color(0xFFE53935)

    val availableDepartments = remember(selectedFaculty, facultyList) {
        if (selectedFaculty == "Diğer") {
            listOf("Diğer")
        } else {
            facultyList.find { it.name == selectedFaculty }?.departments ?: emptyList()
        }
    }

    val gradeList = listOf("Hazırlık", "1.Sınıf", "2.Sınıf", "3.Sınıf", "4.Sınıf", "5.Sınıf", "6.Sınıf", "Mezun")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(figmaBackgroundBase.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // SEÇİLEN AVATARI GÖSTERME
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { showAvatarDialog = true },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = selectedAvatarResId),
                    contentDescription = "Seçilen Avatar",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text("Kişisel Bilgiler", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)

                    Text(
                        "Lütfen bilgilerinizi\ndoğru şekilde doldurunuz.",
                        fontSize = 12.sp,
                        color = if (showError) figmaRedWarning else figmaRedWarning, // Hata varsa kırmızı kalır
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 4.dp, bottom = 24.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    // AD - SOYAD
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        CustomTextField(value = name, onValueChange = { name = it }, label = "Adınız", modifier = Modifier.weight(1f), focusedBorderColor = figmaGreen)
                        CustomTextField(value = surname, onValueChange = { surname = it }, label = "Soyadınız", modifier = Modifier.weight(1f), focusedBorderColor = figmaGreen)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // FAKÜLTE SEÇİMİ
                    val facultyNames = facultyList.map { it.name }
                    DropdownTextField(
                        label = "Fakülteniz",
                        options = facultyNames,
                        selectedOption = selectedFaculty,
                        onOptionSelected = { newFaculty ->
                            selectedFaculty = newFaculty
                            selectedDepartment = ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        focusedBorderColor = figmaGreen
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
                            enabled = selectedFaculty.isNotEmpty(),
                            focusedBorderColor = figmaGreen
                        )
                        DropdownTextField(
                            label = "Sınıf",
                            options = gradeList,
                            selectedOption = selectedGrade,
                            onOptionSelected = { selectedGrade = it },
                            modifier = Modifier.weight(1f),
                            focusedBorderColor = figmaGreen
                        )
                    }

                    if (showError) {
                        Text(
                            "Lütfen tüm alanları doldurunuz!",
                            color = figmaRedWarning,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // --- İLERLE BUTONU (DÜZELTİLDİ) ---
                    Button(
                        onClick = {
                            val fullName = "$name $surname"

                            // Basit Validasyon: Alanlar boş değilse geç
                            if (name.isNotBlank() && surname.isNotBlank() && selectedFaculty.isNotBlank()) {
                                showError = false

                                // ViewModel'in dönüşünü beklemeden direkt MainActivity'e bildiriyoruz
                                // Böylece "İlerle" butonu kesin çalışır.
                                onRegisterSuccess(fullName, selectedAvatarResId)

                                // (Opsiyonel) ViewModel'e de haber verebilirsin ama navigasyonu engellemesin
                                // viewModel.registerUser(...)
                            } else {
                                showError = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = figmaGreen),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(25.dp)
                    ) {
                        Text("İlerle", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Geri dön", color = Color.White, modifier = Modifier.clickable { onLoginClick() })
        }

        if (showAvatarDialog) {
            AvatarSelectionDialog(
                onDismiss = { showAvatarDialog = false },
                onAvatarSelected = { resId ->
                    selectedAvatarResId = resId
                    showAvatarDialog = false
                }
            )
        }
    }
}

// --- YARDIMCI BİLEŞENLER ---

@Composable
fun AvatarSelectionDialog(onDismiss: () -> Unit, onAvatarSelected: (Int) -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Bir avatar seçin", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
                val avatars = listOf(
                    R.drawable.ppgroup1, R.drawable.ppgroup2, R.drawable.ppgroup3,
                    R.drawable.ppgroup4, R.drawable.ppgroup5, R.drawable.ppgroup6,
                    R.drawable.ppgroup7, R.drawable.ppgroup8, R.drawable.ppgroup9
                )

                Column {
                    avatars.chunked(3).forEach { rowAvatars ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            rowAvatars.forEach { resId ->
                                IconButton(onClick = { onAvatarSelected(resId) }, modifier = Modifier.size(70.dp).padding(4.dp)) {
                                    Image(painter = painterResource(id = resId), contentDescription = null, modifier = Modifier.size(60.dp).clip(CircleShape), contentScale = ContentScale.Crop)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownTextField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    focusedBorderColor: Color = Color(0xFF4CAF50)
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { if (enabled) expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedOption, onValueChange = {}, readOnly = true,
            label = { Text(label, fontSize = 12.sp) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White, unfocusedContainerColor = Color.White,
                disabledContainerColor = Color(0xFFF5F5F5), unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = focusedBorderColor, focusedLabelColor = focusedBorderColor
            ),
            enabled = enabled
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }, modifier = Modifier.background(Color.White)) {
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
fun CustomTextField(
    value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier = Modifier, focusedBorderColor: Color = Color(0xFF4CAF50)
) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange, label = { Text(label, fontSize = 12.sp) }, modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White, unfocusedContainerColor = Color.White,
            unfocusedBorderColor = Color.LightGray, focusedBorderColor = focusedBorderColor, focusedLabelColor = focusedBorderColor
        ),
        singleLine = true
    )
}