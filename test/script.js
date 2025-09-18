// ===== VARIÁVEIS GLOBAIS =====
let currentMemberId = null;
let currentSlotNumber = null;
let selectedSealFileName = null;

// ===== FUNÇÕES DE UPLOAD DE FOTOS =====
function triggerFileInput(memberId) {
    document.getElementById(`fileInput${memberId}`).click();
}

function previewImage(memberId, input) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        
        reader.onload = function(e) {
            const photoContainer = input.parentNode;
            const placeholder = photoContainer.querySelector('.photo-placeholder');
            
            // Criar elemento img se não existir
            let img = photoContainer.querySelector('.member-photo');
            if (!img) {
                img = document.createElement('img');
                img.className = 'member-photo';
                img.onclick = () => triggerFileInput(memberId);
                photoContainer.insertBefore(img, placeholder);
            }
            
            img.src = e.target.result;
            placeholder.style.display = 'none';
            img.style.display = 'block';
        };
        
        reader.readAsDataURL(input.files[0]);
    }
}

// ===== FUNÇÕES DE SELEÇÃO DE SELOS =====
function triggerBadgeInput(memberId, slotNumber) {
    currentMemberId = memberId;
    currentSlotNumber = slotNumber;
    openSealSelector();
}

function openSealSelector() {
    const overlay = document.getElementById('sealSelectorOverlay');
    const grid = document.getElementById('sealOptionsGrid');
    
    // Lista dos selos disponíveis
    const sealFiles = [
        'Atitude de Serviço.png',
        'Colaboração.png',
        'Conclusão de Tarefas.png',
        'Diversidade e Inclusão.png',
        'Liderança.png',
        'Profissionalismo.png'
    ];
    
    // Limpar grid anterior
    grid.innerHTML = '';
    
    // Criar opções de selos
    sealFiles.forEach(fileName => {
        const option = document.createElement('div');
        option.className = 'seal-option';
        option.onclick = () => selectSeal(fileName);
        
        const img = document.createElement('img');
        img.src = `icons/${fileName}`;
        img.alt = fileName;
        
        const name = document.createElement('div');
        name.className = 'seal-option-name';
        name.textContent = fileName.replace(/\.[^/.]+$/, "");
        
        option.appendChild(img);
        option.appendChild(name);
        grid.appendChild(option);
    });
    
    const removeBtn = document.querySelector('.seal-action-btn.remove');
    if (currentMemberId && currentSlotNumber) {
        const badgeSlot = document.querySelector(`#badgeInput${currentMemberId}_${currentSlotNumber}`).parentNode;
        const hasImage = badgeSlot.querySelector('img');
        
        if (hasImage && removeBtn) {
            removeBtn.style.display = 'inline-block';
        } else if (removeBtn) {
            removeBtn.style.display = 'none';
        }
    }
    
    // Mostrar overlay com animação
    overlay.classList.add('active');
}

function closeSealSelector() {
    const overlay = document.getElementById('sealSelectorOverlay');
    overlay.classList.remove('active');
    currentMemberId = null;
    currentSlotNumber = null;
}

function removeCurrentSeal() {
    if (currentMemberId && currentSlotNumber) {
        const badgeSlot = document.querySelector(`#badgeInput${currentMemberId}_${currentSlotNumber}`).parentNode;
        const img = badgeSlot.querySelector('img');
        const placeholder = badgeSlot.querySelector('.placeholder-text');
        const fileInput = badgeSlot.querySelector('.badge-file-input');
        const badgeWrapper = badgeSlot.parentNode;
        const badgeName = badgeWrapper.querySelector('.badge-name');
        
        if (img) {
            img.remove();
        }
        
        placeholder.style.display = 'block';
        badgeSlot.classList.remove('filled');
        fileInput.value = ''; // Limpa o input
        
        // Limpar o nome do selo
        if (badgeName) {
            badgeName.textContent = '';
        }
        
        closeSealSelector();
    }
}

function selectSeal(fileName) {
    if (currentMemberId && currentSlotNumber) {
        // Verificar se o selo já está sendo usado pelo mesmo membro
        const existingSlot = getSealSlotByMember(currentMemberId, fileName);
        if (existingSlot && existingSlot !== currentSlotNumber) {
            // Remover o selo do slot anterior
            removeSealFromSlot(currentMemberId, existingSlot);
        }
        
        // Aplicar selo diretamente
        applySealToMember(currentMemberId, currentSlotNumber, fileName);
        closeSealSelector();
    }
}

// ===== FUNÇÕES AUXILIARES PARA GERENCIAMENTO DE SELOS =====
function getSealSlotByMember(memberId, fileName) {
    // Verificar os dois slots do membro
    for (let slot = 1; slot <= 2; slot++) {
        const badgeSlot = document.querySelector(`#badgeInput${memberId}_${slot}`).parentNode;
        const img = badgeSlot.querySelector('img');
        
        if (img) {
            // Verificar tanto arquivos da pasta icons/ quanto uploads customizados
            const currentSealName = img.src.split('/').pop();
            const customFileName = img.getAttribute('data-filename');
            
            if (currentSealName === fileName || customFileName === fileName) {
                return slot;
            }
        }
    }
    return null;
}

function removeSealFromSlot(memberId, slotNumber) {
    const badgeSlot = document.querySelector(`#badgeInput${memberId}_${slotNumber}`).parentNode;
    const img = badgeSlot.querySelector('img');
    const placeholder = badgeSlot.querySelector('.placeholder-text');
    const badgeWrapper = badgeSlot.parentNode;
    const badgeName = badgeWrapper.querySelector('.badge-name');
    
    if (img) {
        img.remove();
    }
    
    placeholder.style.display = 'block';
    badgeSlot.classList.remove('filled');
    
    // Limpar o nome do selo
    if (badgeName) {
        badgeName.textContent = '';
    }
}

// ===== FUNÇÕES DE APLICAÇÃO DE SELOS =====
function applyCustomSealToMember(memberId, slotNumber, file, fileData) {
    const badgeSlot = document.querySelector(`#badgeInput${memberId}_${slotNumber}`).parentNode;
    const placeholder = badgeSlot.querySelector('.placeholder-text');
    const badgeWrapper = badgeSlot.parentNode;
    const badgeName = badgeWrapper.querySelector('.badge-name');
    
    // Criar elemento img se não existir
    let img = badgeSlot.querySelector('img');
    if (!img) {
        img = document.createElement('img');
        badgeSlot.appendChild(img);
    }
    
    img.src = fileData;
    img.setAttribute('data-filename', file.name);
    placeholder.style.display = 'none';
    badgeSlot.classList.add('filled');
    
    // Atualizar o nome do selo
    if (badgeName) {
        const nameWithoutExtension = file.name.replace(/\.[^/.]+$/, "");
        badgeName.textContent = nameWithoutExtension;
    }
}

function applySealToMember(memberId, slotNumber, fileName) {
    const badgeSlot = document.querySelector(`#badgeInput${memberId}_${slotNumber}`).parentNode;
    const placeholder = badgeSlot.querySelector('.placeholder-text');
    const badgeWrapper = badgeSlot.parentNode;
    const badgeName = badgeWrapper.querySelector('.badge-name');
    
    // Criar elemento img se não existir
    let img = badgeSlot.querySelector('img');
    if (!img) {
        img = document.createElement('img');
        badgeSlot.appendChild(img);
    }
    
    img.src = `icons/${fileName}`;
    img.setAttribute('data-filename', fileName); // Guardar nome do arquivo para validação
    placeholder.style.display = 'none';
    badgeSlot.classList.add('filled');
    
    // Atualizar o nome do selo com o nome do arquivo
    if (badgeName) {
        const nameWithoutExtension = fileName.replace(/\.[^/.]+$/, "");
        badgeName.textContent = nameWithoutExtension;
    }
}

// ===== FUNÇÕES DE UPLOAD CUSTOMIZADO =====
function triggerCustomUpload() {
    document.getElementById('customSealInput').click();
}

function handleCustomSealUpload(input) {
    if (input.files && input.files[0] && currentMemberId && currentSlotNumber) {
        const file = input.files[0];
        
        // Verificar se o arquivo já está sendo usado pelo mesmo membro
        const existingSlot = getSealSlotByMember(currentMemberId, file.name);
        if (existingSlot && existingSlot !== currentSlotNumber) {
            // Remover o selo do slot anterior
            removeSealFromSlot(currentMemberId, existingSlot);
        }
        
        // Aplicar selo customizado diretamente
        if (currentMemberId && currentSlotNumber) {
            const reader = new FileReader();
            reader.onload = function(e) {
                applyCustomSealToMember(currentMemberId, currentSlotNumber, file, e.target.result);
                closeSealSelector();
            };
            reader.readAsDataURL(file);
        }
    }
}

// ===== FUNÇÕES PARA PREVIEW E REMOÇÃO DIRETA DE BADGES =====
function previewBadge(memberId, slotNumber, input) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        const fileName = input.files[0].name;
        
        reader.onload = function(e) {
            const badgeSlot = input.parentNode;
            const placeholder = badgeSlot.querySelector('.placeholder-text');
            const badgeWrapper = badgeSlot.parentNode;
            const badgeName = badgeWrapper.querySelector('.badge-name');
            
            // Extrair nome do arquivo sem extensão
            const nameWithoutExtension = fileName.replace(/\.[^/.]+$/, "");
            
            // Criar elemento img se não existir
            let img = badgeSlot.querySelector('img');
            if (!img) {
                img = document.createElement('img');
                badgeSlot.appendChild(img);
            }
            
            img.src = e.target.result;
            placeholder.style.display = 'none';
            badgeSlot.classList.add('filled');
            
            // Atualizar o nome do selo com o nome do arquivo
            if (badgeName) {
                badgeName.textContent = nameWithoutExtension;
            }
        };
        
        reader.readAsDataURL(input.files[0]);
    }
}

function removeBadge(memberId, slotNumber, event) {
    event.stopPropagation(); // Previne a propagação do click
    
    const badgeSlot = event.target.parentNode;
    const img = badgeSlot.querySelector('img');
    const placeholder = badgeSlot.querySelector('.placeholder-text');
    const fileInput = badgeSlot.querySelector('.badge-file-input');
    const badgeWrapper = badgeSlot.parentNode;
    const badgeName = badgeWrapper.querySelector('.badge-name');
    
    if (img) {
        img.remove();
    }
    
    placeholder.style.display = 'block';
    badgeSlot.classList.remove('filled');
    fileInput.value = ''; // Limpa o input
    
    // Limpar o nome do selo
    if (badgeName) {
        badgeName.textContent = '';
    }
}

// ===== FUNÇÕES UTILITÁRIAS =====
function updateDisplay(memberId) {
    // Esta função pode ser expandida para validações ou outras funcionalidades
    console.log(`Membro ${memberId} atualizado`);
}

// ===== INICIALIZAÇÃO E EVENT LISTENERS =====
window.addEventListener('load', function() {
    console.log('Layout da equipe carregado!');
    
    // Adicionar evento para fechar painel ao clicar fora
    const overlay = document.getElementById('sealSelectorOverlay');
    overlay.addEventListener('click', function(e) {
        if (e.target === overlay) {
            closeSealSelector();
        }
    });
});