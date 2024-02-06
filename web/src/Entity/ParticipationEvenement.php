<?php

namespace App\Entity;

use App\Repository\ParticipationEvenementRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: ParticipationEvenementRepository::class)]
class ParticipationEvenement
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $dateInscriptionEvenement = null;

    #[ORM\Column(length: 255)]
    private ?string $statutParticipation = null;

    #[ORM\ManyToOne(inversedBy: 'participation')]
    private ?Evenement $evenement = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getDateInscriptionEvenement(): ?\DateTimeInterface
    {
        return $this->dateInscriptionEvenement;
    }

    public function setDateInscriptionEvenement(\DateTimeInterface $dateInscriptionEvenement): self
    {
        $this->dateInscriptionEvenement = $dateInscriptionEvenement;

        return $this;
    }

    public function getStatutParticipation(): ?string
    {
        return $this->statutParticipation;
    }

    public function setStatutParticipation(string $statutParticipation): self
    {
        $this->statutParticipation = $statutParticipation;

        return $this;
    }

    public function getEvenement(): ?Evenement
    {
        return $this->evenement;
    }

    public function setEvenement(?Evenement $evenement): self
    {
        $this->evenement = $evenement;

        return $this;
    }
}
