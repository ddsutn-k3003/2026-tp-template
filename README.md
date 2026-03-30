# 🧪 2026 - Trabajo Práctico Anual - Template

## 👤 Datos del Alumno
- **Nombre:**  
- **Apellido:**  

---

## 🧩 Componente Desarrollado
- 

---

## ⚙️ Guía de Sincronización con el Template

Para poder recibir **actualizaciones** de enunciados, correcciones de tests o cambios en el entorno de evaluación (GitHub Actions), seguí estos pasos para mantener tu repositorio vinculado al template original.

---

### 🔹 1. Configuración inicial (solo una vez)

Inmediatamente después de clonar tu repositorio de GitHub Classroom, abrí una terminal dentro de la carpeta del proyecto y ejecutá:

```bash
# Vincular el repositorio base
git remote add template https://github.com/ddsutn-k3003/2026-tp-template

# Descargar información del origen
git fetch template

# Unificar historiales
git merge template/main --allow-unrelated-histories
```
Listo, los repositorios ya deberían estar vinculados correctamente.

---

### 🔹 2. Para recibir actualizaciones de la cátedra (Previamente avisaremos por el canal #avisos del Discord)

Cada vez que se anuncien cambios o correcciones en el TP, ejecutá estos comandos:

```bash
# Traer los nuevos cambios
git fetch template

# Integrar los cambios a tu rama actual
git merge template/main
```
📌 **Nota:**  
A la hora de recibir actualizaciones, no se pone la flag "--allow-unrelated-histories" en el merge.

---

### ⚠️ Importante

**ARCHIVOS PROTEGIDOS:**

> Los archivos de las carpetas "/catedra" y ".github/" están **PROTEGIDOS**, es decir, **NO PUEDEN MODIFICARLOS**.
Modificar estos archivos implica **desaprobar inmediatamente la instancia de entrega del TPA**.
