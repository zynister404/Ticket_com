Ticket_Com - Android Application
Aplikasi reservasi tiket berbasis Android yang dirancang untuk memberikan pengalaman pemesanan yang cepat dan mudah. Proyek ini dikembangkan menggunakan Android Studio dengan manajemen dependensi modern melalui Version Catalog.
🚀 Panduan Instalasi & Troubleshooting
1. Mengatasi Error XML (Processing Instruction)
Jika muncul pesan error The processing instruction target matching "[xX][mM][lL]" is not allowed:

Pastikan file AndroidManifest.xml atau file XML di dalam folder res/xml/ dimulai langsung dengan tag <?xml ... ?> pada baris ke-1.

Pastikan tidak ada spasi, karakter tersembunyi, atau baris kosong sebelum tag tersebut.

2. Mengatasi Resource Not Found (Ikon Aplikasi)
Jika build gagal karena @mipmap/ic_launcher atau ic_launcher_round dianggap hilang:

Klik kanan pada folder res di panel project.

Pilih New > Image Asset.

Gunakan pengaturan default (Launcher Icons) dan klik Next lalu Finish.

Langkah ini akan men-generate ulang file .webp yang diperlukan untuk ikon aplikasi.

3. Sinkronisasi Gradle (Version Catalog)
Aplikasi ini menggunakan sistem libs.versions.toml. Jika muncul error Unresolved reference: libs:

Klik tombol Sync Project with Gradle Files (ikon gajah biru di pojok kanan atas).

Jika masih bermasalah, lakukan File > Invalidate Caches... > Invalidate and Restart.
🛠️ Tech Stack
Bahasa: Kotlin / Java

Build System: Gradle Kotlin DSL (.kts)

Manajemen Dependensi: Gradle Version Catalog

Minimum SDK: API 24 (Android 7.0)

Target SDK: API 34 (Android 14)

Build Tools: Android Gradle Plugin (AGP) 8.13.2
📂 Struktur Activity
Daftar halaman yang terdaftar dalam aplikasi:
Activity,Deskripsi
MainActivity,Halaman utama dan gerbang masuk aplikasi (Launcher).
LoginActivity,Layanan autentikasi pengguna masuk.
RegisterActivity,Pendaftaran akun pengguna baru.
HomeActivity,Dashboard utama untuk mencari dan memilih tiket.
📦 Cara Menjalankan
Clone Repositori:

Bash

git clone https://github.com/username/Ticket_Com.git
Buka Proyek: Gunakan Android Studio (disarankan versi Ladybug atau yang lebih baru).

Build & Run: Tunggu proses Gradle selesai, lalu klik ikon Run (segitiga hijau).

📄 Lisensi
Copyright © 2026 Ticket_Com Team. Seluruh hak cipta dilindungi undang-undang.
